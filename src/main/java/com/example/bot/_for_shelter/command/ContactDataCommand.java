package com.example.bot._for_shelter.command;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

public class ContactDataCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public ContactDataCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;


    }

    @Override
    public void execute(Update update) {

        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String messageText = update.getCallbackQuery().getMessage().getText();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();

        String name = update.getCallbackQuery().getMessage().getFrom().getFirstName();
        String lastName = update.getCallbackQuery().getMessage().getFrom().getLastName();

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton contactButton = new KeyboardButton();
        contactButton.setText("Отправить номер телефона");
        contactButton.setRequestContact(true); // Запрашиваем номер

        KeyboardRow row = new KeyboardRow();
        row.add(contactButton);

        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true); // Уменьшает размер кнопок, чтобы они занимали меньше места
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остаётся видимой после нажатия кнопки
        keyboardMarkup.setSelective(true); // Отображает клавиатуру только определённым пользователям

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Пожалуйста, отправьте ваш номер телефона:");
        message.setReplyMarkup(keyboardMarkup);

        sendBotMessageService.sendMessage(message, messageId);
    }
}

