package com.example.bot._for_shelter.command;

import com.vdurmont.emoji.EmojiParser;
import org.aspectj.bridge.ICommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminOrUserCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public AdminOrUserCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        String adminButtonText = EmojiParser.parseToUnicode("Я админ " + " :cold_face:");
        String userButtonText = EmojiParser.parseToUnicode("Я user " + " :ghost:");
        var adminButton = new InlineKeyboardButton();
        adminButton.setText(adminButtonText);
        adminButton.setCallbackData("admin-button");

        var informationButton = new InlineKeyboardButton();
        informationButton.setText(userButtonText);
        informationButton.setCallbackData("user-button");


        rowInLine1.add(informationButton);
        rowInLine1.add(adminButton);
        rowsInLine.add(rowInLine1);

        markupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(markupInLine);
        sendMessage.setText("Вы админ или пользователь ");
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals("/start");
    }
}
