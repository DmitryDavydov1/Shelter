package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.DTO.AdoptionDTO;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для управления процессом усыновления животных.
 * Включает методы для добавления усыновлений, проверки статуса и отправки сообщений пользователям.
 */
@Service
public class AdoptionService {

    @Autowired
    AdoptionRepository adoptionRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SendBotMessageService sendBotMessageService;

    /**
     * Добавляет новое усыновление.
     *
     * @param adoptionDTO DTO для создания нового усыновления.
     * @return id нового объекта Adoption.
     * @throws EntityNotFoundException если питомец или пользователь с указанными ID не найдены.
     */

    @Caching(evict = {
            @CacheEvict(value = "have-adoption", allEntries = true),
            @CacheEvict(value = "haveAdoption", key = "#adoptionDTO.bot_user_id")
    })
    public Long addAdoption(AdoptionDTO adoptionDTO) {
        Adoption adoption = new Adoption();
        Pet pet = petRepository.findById(adoptionDTO.getPet_id())
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with ID: " + adoptionDTO.getPet_id()));
        BotUser botUser = userRepository.findById(adoptionDTO.getBot_user_id())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + adoptionDTO.getBot_user_id()));
        adoption.setPet(pet);
        adoption.setBotUser(botUser);
        adoption.setLastDay(30);
        adoption.setCurrentDay(0);
        return adoptionRepository.save(adoption).getId();
    }

    /**
     * Проверяет, есть ли у пользователя активное усыновление.
     *
     * @param userId ID пользователя.
     * @return true, если у пользователя есть активное усыновление, иначе false.
     */
    @Cacheable("haveAdoption")
    public boolean userHaveAdoptionOrNo(Long userId) {
        return adoptionRepository.existsByBotUserId(userId);
    }

    /**
     * Обновляет счетчик дней в процессе усыновления, и отправляет уведомления,
     * если условие 30, 44 или 60 дней выполняется.
     */
    public void addOneDay() {
        adoptionRepository.findAll().forEach(adoption -> {
            adoption.setCurrentDay(adoption.getCurrentDay() + 1);
            adoptionRepository.save(adoption);
        });
        adoptionRepository.findAll().forEach(adoption -> {
            if (adoption.getCurrentDay() == 30) {
                SendMessage sendMessage = new SendMessage();
                BotUser botUser = userRepository.findByPhoneNumber("+79961382430");
                sendMessage.setChatId(botUser.getChatId());
                InlineKeyboardMarkup buttons = buttonForAdoptionWith30Days(adoption.getId());
                sendMessage.setReplyMarkup(buttons);
                sendMessage.setText("Усыновитель " + adoption.getBotUser().getName());
                sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
            }
            if ((adoption.getCurrentDay() == 44 && adoption.getLastDay() == 44) ||
                    (adoption.getCurrentDay() == 60 && adoption.getLastDay() == 60)) {
                SendMessage sendMessage = new SendMessage();
                BotUser botUser = userRepository.findByPhoneNumber("+79961382430");
                sendMessage.setChatId(botUser.getChatId());
                InlineKeyboardMarkup buttons = buttonForAdoptionWith44Or60Days(adoption.getId());
                sendMessage.setReplyMarkup(buttons);
                sendMessage.setText("Усыновитель " + adoption.getBotUser().getName());
                sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
            }
        });
    }

    /**
     * Создает клавиатуру для кнопок с возможностью управления статусом усыновления на 30-й день.
     *
     * @param id ID усыновления.
     * @return {@link InlineKeyboardMarkup} с кнопками.
     */
    public InlineKeyboardMarkup buttonForAdoptionWith30Days(Long id) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();

        // Кнопка "Испытательный срок пройден"
        String succesfullAdoptionButtonText = EmojiParser.parseToUnicode("Испытательный срок пройден");
        var succesfullAdoptionButton = new InlineKeyboardButton();
        succesfullAdoptionButton.setText(succesfullAdoptionButtonText);
        succesfullAdoptionButton.setCallbackData("успешный-срок-" + id);
        rowInLine1.add(succesfullAdoptionButton);

        // Кнопка "Продление на 14 дней"
        String extensionTermBy14ButtonText = EmojiParser.parseToUnicode("Испытательный срок продлен на 14");
        var extensionTermBy14Button = new InlineKeyboardButton();
        extensionTermBy14Button.setText(extensionTermBy14ButtonText);
        extensionTermBy14Button.setCallbackData("продление-срока-14-" + id);
        rowInLine2.add(extensionTermBy14Button);

        // Кнопка "Продление на 30 дней"
        String extensionTermBy30ButtonText = EmojiParser.parseToUnicode("Испытательный срок продлен на 30");
        var extensionTermBy30Button = new InlineKeyboardButton();
        extensionTermBy30Button.setText(extensionTermBy30ButtonText);
        extensionTermBy30Button.setCallbackData("продление-срока-30-" + id);
        rowInLine3.add(extensionTermBy30Button);

        // Кнопка "Не прошел испытательный срок"
        String unsuccessfulProbationPeriodButtonText = EmojiParser.parseToUnicode("Не прошел испытательный срок");
        var unsuccessfulProbationPeriodButton = new InlineKeyboardButton();
        unsuccessfulProbationPeriodButton.setText(unsuccessfulProbationPeriodButtonText);
        unsuccessfulProbationPeriodButton.setCallbackData("неудачный-пробный-срок-" + id);
        rowInLine4.add(unsuccessfulProbationPeriodButton);

        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);

        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    /**
     * Создает клавиатуру для кнопок с возможностью управления статусом усыновления на 44-й и 60-й день.
     *
     * @param id ID усыновления.
     * @return {@link InlineKeyboardMarkup} с кнопками.
     */
    public InlineKeyboardMarkup buttonForAdoptionWith44Or60Days(Long id) {
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();

        // Кнопка "Испытательный срок пройден"
        String succesfullAdoptionButtonText = EmojiParser.parseToUnicode("Испытательный срок пройден");
        var succesfullAdoptionButton = new InlineKeyboardButton();
        succesfullAdoptionButton.setText(succesfullAdoptionButtonText);
        succesfullAdoptionButton.setCallbackData("успешный-срок-" + id);
        rowInLine1.add(succesfullAdoptionButton);

        // Кнопка "Испытательный срок не пройден"
        String extensionTermBy14ButtonText = EmojiParser.parseToUnicode("Испытательный срок не пройден");
        var extensionTermBy14Button = new InlineKeyboardButton();
        extensionTermBy14Button.setText(extensionTermBy14ButtonText);
        extensionTermBy14Button.setCallbackData("доп-неуспешный-срок-" + id);
        rowInLine2.add(extensionTermBy14Button);

        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);

        markupInLine.setKeyboard(rowsInLine);
        return markupInLine;
    }

    /**
     * Проверяет, существует ли усыновление с указанным chatId.
     *
     * @param chatId ID чата пользователя.
     * @return true, если усыновление найдено, иначе false.
     */
    @Cacheable("have-adoption")
    public boolean findByChatId(Long chatId) {
        return adoptionRepository.findAll().stream()
                .anyMatch(adoption -> adoption.getBotUser().getChatId().equals(String.valueOf(chatId)));
    }


    public Adoption findByChatId(Integer chatId) {
        Adoption adoption = adoptionRepository.findById(chatId).orElse(null);
        return adoption;
    }

    public void saveAdoption(Adoption adoption) {
        adoptionRepository.save(adoption);
    }

    @Caching(evict = {
            @CacheEvict(value = "haveAdoption", key = "#adoption.botUser.id"),
            @CacheEvict(value = "have-adoption", key = "T(java.lang.Long).parseLong(#adoption.botUser.chatId)")
    })
    public void deleteAdoption(Adoption adoption) {
        adoptionRepository.delete(adoption);
    }
}
