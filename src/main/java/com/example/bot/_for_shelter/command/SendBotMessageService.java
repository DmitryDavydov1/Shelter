package com.example.bot._for_shelter.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendBotMessageService {


    void sendMessage(SendMessage message, Integer chatId);

    void sendMessage(SendMessage message, Integer chatId, String command);


    void sendMessageWithKeyboardMarkup(SendMessage message);
}
