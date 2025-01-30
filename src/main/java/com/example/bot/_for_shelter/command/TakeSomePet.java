package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.AdoptionDTO;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import com.example.bot._for_shelter.service.AdoptionService;
import com.example.bot._for_shelter.service.PetService;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class TakeSomePet implements Command {

    private final AdoptionService adoptionService;
    private final UserRepository userRepository;
    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;

    public TakeSomePet(AdoptionService adoptionService, UserRepository userRepository, SendBotMessageService sendBotMessageService, PetService petService) {
        this.adoptionService = adoptionService;
        this.userRepository = userRepository;
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
    }

    @Override
    public void execute(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        String command = update.getCallbackQuery().getData();
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);
        SendMessage sendMessage = new SendMessage();


        if (userRepository.findByChatId(chatId) == null) {
            sendMessage.setChatId(chatId);
            sendMessage.setText("Сначала запиши контактные данные");
            ReplyKeyboardMarkup contactButton = contactButton();
            sendMessage.setReplyMarkup(contactButton);
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        }


        if (matcher.find()) {
            Long userId = userRepository.findByChatId(chatId).getId();
            if (adoptionService.userHaveAdoptionOrNo(userId)) {
                sendMessage.setChatId(chatId);
                sendMessage.setText("Уже есть у вас животное для усыновления");
                sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
            } else {
                Long petId = Long.parseLong(matcher.group(2));
                AdoptionDTO adoptionDTO = new AdoptionDTO();
                adoptionDTO.setPet_id(petId); // Здесь мы уверены, что есть совпадение
                adoptionDTO.setBot_user_id(userId);
                adoptionService.addAdoption(adoptionDTO);
                petService.setHaveOwner(petId);
            }
        }
    }

    @Override
    public boolean isSupport(String command) {
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            String group1 = matcher.group(1);
            return group1.equals("take-this-animal-");
        } else {
            System.out.println("No match found for command: " + command);
        }

        return false;
    }

    public ReplyKeyboardMarkup contactButton() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton contactButton = new KeyboardButton();
        contactButton.setText("Отправить номер телефона");
        contactButton.setRequestContact(true); // Запрашиваем номер

        KeyboardRow row = new KeyboardRow();
        row.add(contactButton);

        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true); // Уменьшает размер кнопок, чтобы они занимали меньше места
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остаётся видимой после нажатия кнопки
        keyboardMarkup.setSelective(true);
        return keyboardMarkup;
    }


}

