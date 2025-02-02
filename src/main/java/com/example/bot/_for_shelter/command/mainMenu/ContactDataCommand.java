package com.example.bot._for_shelter.command.mainMenu;


import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.command.SendBotMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

import static com.example.bot._for_shelter.command.CommandName.contactData;

@Component
public class ContactDataCommand implements Command {
    @Lazy
    private final SendBotMessageService sendBotMessageService;

    public ContactDataCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;


    }

    @Override
    public void execute(Update update) {

        Long chatId = update.getCallbackQuery().getMessage().getChatId();



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

        sendBotMessageService.sendMessageWithKeyboardMarkup(message);
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(contactData.getCommandName());
    }

}

