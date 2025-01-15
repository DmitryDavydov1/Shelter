package com.example.bot._for_shelter.command;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;

    }

    @Override
    public void execute(Update update) {
        long chatId = 0;
        String answer = "Hi ";
        int messageId = 0;

        // Проверяем, есть ли сообщение или обратный вызов
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            answer = "Hi " + update.getMessage().getFrom().getFirstName() + ", nice to meet you";
            messageId = update.getMessage().getMessageId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            answer = "Hi " + update.getCallbackQuery().getFrom().getFirstName() + ", nice to meet you";
            messageId = update.getCallbackQuery().getMessage().getMessageId();
        }

        System.out.println(chatId);

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(answer);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var dogButton = new InlineKeyboardButton();
        String dogButtonText = EmojiParser.parseToUnicode("Собаки" + " :dog:");

        dogButton.setText(dogButtonText);
        dogButton.setCallbackData("dog-button");

        var catButton = new InlineKeyboardButton();
        String catButtonText = EmojiParser.parseToUnicode("Кошки" + " :cat:");
        catButton.setText(catButtonText);
        catButton.setCallbackData("cat-button");

        rowInLine.add(dogButton);
        rowInLine.add(catButton);

        rowsInLine.add(rowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);


        sendBotMessageService.sendMessage(message, messageId);

    }
}
