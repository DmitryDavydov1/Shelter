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


        if (matcher.find()) {
            Long userId = userRepository.findByChatId(chatId).getId();
            if (adoptionService.userHaveAdoptionOrNo(userId)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Уже есть");
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
}

