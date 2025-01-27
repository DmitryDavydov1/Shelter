package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.AdoptionDTO;
import com.example.bot._for_shelter.repository.UserRepository;
import com.example.bot._for_shelter.service.AdoptionService;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class TakeSomePet implements Command {

    private final AdoptionService adoptionService;
    private final UserRepository userRepository;

    public TakeSomePet(AdoptionService adoptionService, UserRepository userRepository) {
        this.adoptionService = adoptionService;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        String command = update.getCallbackQuery().getData();
        Pattern pattern = Pattern.compile("(.*-)(\\d+)$");
        Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            AdoptionDTO adoptionDTO = new AdoptionDTO();
            adoptionDTO.setPet_id(Long.valueOf(matcher.group(2))); // Здесь мы уверены, что есть совпадение
            Long userId = userRepository.findByChatId(chatId).getId();
            adoptionDTO.setBot_user_id(Long.valueOf(userId));
            adoptionService.addAdoption(adoptionDTO);
        } else {
            // Логируем или обрабатываем случай, когда совпадение не найдено
            System.out.println("No match found for command: " + command);
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
            // Логируем или обрабатываем случай, когда совпадение не найдено
            System.out.println("No match found for command: " + command);
        }

        return false;
    }
}

