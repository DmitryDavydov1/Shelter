package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.repository.PetRepository;
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

    private final PetRepository petRepository;
    private final SendBotMessageService sendBotMessageService;

    public ShowsAllAnimals(PetRepository petRepository, SendBotMessageService sendBotMessageService) {
        this.petRepository = petRepository;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {


        petRepository.findAll().forEach(pet -> {
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
            sendMessage.setText("Возраст: " + pet.getAge() +
                    "Пол: " + pet.getGender());
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        });

    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(takeAnimalFromShelter.getCommandName());
    }
}
