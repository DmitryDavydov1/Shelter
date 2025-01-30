package com.example.bot._for_shelter.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public interface SendBotMessageService {


    void sendMessage(SendMessage message, Integer chatId);

    void sendMessage(SendMessage message, Integer chatId, String command);


    void sendMessageWithKeyboardMarkup(SendMessage message);

    void sendPhoto(SendPhoto sendPhoto);
}
