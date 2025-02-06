package com.example.bot._for_shelter.command.adoptionInfo;

import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.service.PetService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.*;

@Component
public class ShowsAllAnimals implements Command {


    private final SendBotMessageService sendBotMessageService;
    private final PetService petService;

    public ShowsAllAnimals(SendBotMessageService sendBotMessageService, PetService petService) {
        this.sendBotMessageService = sendBotMessageService;
        this.petService = petService;
    }

    @Override
    public void execute(Update update) {


        petService.findAllByHaveOwner(false).forEach(pet -> {
            SendMessage sendMessage = new SendMessage();
            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
            String informationButtonText = EmojiParser.parseToUnicode("Выбрать это животное" + " :cat2:");
            var informationButton = new InlineKeyboardButton();
            informationButton.setText(informationButtonText);
            informationButton.setCallbackData("take-this-animal-" + pet.getId());
            rowInLine1.add(informationButton);
            rowsInLine.add(rowInLine1);
            System.out.println("take-this-animal-" + pet.getId());
            markupInLine.setKeyboard(rowsInLine);
            sendMessage.setReplyMarkup(markupInLine);
            sendMessage.setText("Возраст: " + pet.getAge() + "\n" +
                    "Пол: " + pet.getGender() + "\n" +
                    "Рост: " + pet.getHeight() + "\n" +
                    "Кличка: " + pet.getNickname() + "\n" +
                    "Вес: " + pet.getWeight());
            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        });

    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(takeAnimalFromShelter.getCommandName());
    }
}
