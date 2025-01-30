package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.TelegramBot;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.callVolunteer;

@Component
public class CallVolunteer implements Command {


    private final SendBotMessageService sendBotMessageService;
    private final TelegramBot telegramBot;

    @Autowired
    @Lazy
    public CallVolunteer(SendBotMessageService sendBotMessageService, TelegramBot telegramBot) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramBot = telegramBot;
    }

    @Override
    public void execute(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getCallbackQuery().getMessage().getChatId().toString());
        message.setText("Id волонтера- @Butterfl_1y");
        int messageId = update.getCallbackQuery().getMessage().getMessageId();


        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();


        var informationButton = new InlineKeyboardButton();
        String informationButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
        informationButton.setText(informationButtonText);
        informationButton.setCallbackData("back-to-start-button");


        rowInLine1.add(informationButton);


        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);
        sendBotMessageService.sendMessage(message, messageId);
        SendPhoto msg = SendPhoto
                .builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                .photo(new InputFile("AgACAgIAAxkBAAIFRGebPi0gntIu0AABAUShXiWAgEn5xwACR-ExG2Jq2EgfoiWL5MiwSQEAAwIAA3kAAzYE"))
                .build();
        telegramBot.sendPhoto(msg);
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(callVolunteer.getCommandName());
    }
}
