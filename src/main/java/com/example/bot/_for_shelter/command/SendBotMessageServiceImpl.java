package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TelegramBot bot;

    @Autowired
    @Lazy
    public SendBotMessageServiceImpl(TelegramBot bot) {
        this.bot = bot;
    }


    @Override
    public void sendMessage(SendMessage message, Integer chatId) {
        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(message.getText());
        sendMessage.setMessageId(chatId);
        sendMessage.setReplyMarkup((InlineKeyboardMarkup) message.getReplyMarkup());


        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendMessage(SendMessage message, Integer chatId, String command) {
        if (message.getText().startsWith("Hi")) {
            try {
                bot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendMessageWithKeyboardMarkup(SendMessage message) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPhoto(SendPhoto sendPhoto){
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
