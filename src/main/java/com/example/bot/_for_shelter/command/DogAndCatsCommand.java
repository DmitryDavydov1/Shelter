package com.example.bot._for_shelter.command;

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
public class DogAndCatsCommand implements Command {
    @Override
    public void execute(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String text = "Выберите пункт";
        SendMessage message = SendMessage // Create a message object
                .builder()
                .chatId(String.valueOf(chatId))
                .text(text)
                .build();

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine4 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine5 = new ArrayList<>();

        var informationButton = new InlineKeyboardButton();
        String informationButtonText = EmojiParser.parseToUnicode("Узнать информацию о приюте" + " :information_source:");
        informationButton.setText(informationButtonText);
        informationButton.setCallbackData("information-button");

        var takeAnimalButton = new InlineKeyboardButton();
        String takeAnimalButtonText = EmojiParser.parseToUnicode("Как взять животное из приюта " + " :cat:");
        takeAnimalButton.setText(takeAnimalButtonText);
        takeAnimalButton.setCallbackData("takeAnimal-button");

        var petReportButtonButton = new InlineKeyboardButton();
        String petReportButtonText = EmojiParser.parseToUnicode("Прислать отчет о питомце" + " :dog:");
        petReportButtonButton.setText(petReportButtonText);
        petReportButtonButton.setCallbackData("petReport-button");


        var callVolunteerButton = new InlineKeyboardButton();
        String callVolunteerButtonText = EmojiParser.parseToUnicode("Позвать волонтера" + " :boy:");
        callVolunteerButton.setText(callVolunteerButtonText);
        callVolunteerButton.setCallbackData("callVolunteer-button");

        var backButton = new InlineKeyboardButton();
        String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        backButton.setText(backButtonButtonText);
        backButton.setCallbackData("back-to-start-button");

        rowInLine1.add(informationButton);
        rowInLine2.add(petReportButtonButton);
        rowInLine3.add(takeAnimalButton);
        rowInLine4.add(callVolunteerButton);
        rowInLine5.add(backButton);


        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);
        rowsInLine.add(rowInLine4);
        rowsInLine.add(rowInLine5);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        sendBotMessageService.sendMessage(message, messageId);
    }

    private final SendBotMessageService sendBotMessageService;

    public DogAndCatsCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;


    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(DOG.getCommandName()) || command.equals(CAT.getCommandName());
    }
}
