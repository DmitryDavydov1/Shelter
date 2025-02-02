package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.repository.UserRepository;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

@Component
public class IAmAdminCommand implements Command {

    private final UserRepository userRepository;
    private final SendBotMessageService sendBotMessageService;


    public IAmAdminCommand(UserRepository userRepository, SendBotMessageService sendBotMessageService) {
        this.userRepository = userRepository;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        SendMessage sendMessage = new SendMessage();
        if (userRepository.findByChatId(chatId) == null) {

            sendMessage.setChatId(chatId);
            sendMessage.setText("Сначала запиши контактные данные");
            ReplyKeyboardMarkup contactButton = contactButton();
            sendMessage.setReplyMarkup(contactButton);
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        }
        if (userRepository.findByChatId(chatId).getPhoneNumber().equals("+79961382430")) {
            sendMessage.setChatId(chatId);
            sendMessage.setText("/watchCommand - команда для просмотра репортов");
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        } else {
            sendMessage.setChatId(chatId);
            sendMessage.setText("ты не админ");
            sendBotMessageService.sendMessageWithKeyboardMarkup(sendMessage);
        }
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals("admin-button");
    }


    public ReplyKeyboardMarkup contactButton() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardButton contactButton = new KeyboardButton();
        contactButton.setText("Отправить номер телефона");
        contactButton.setRequestContact(true); // Запрашиваем номер

        KeyboardRow row = new KeyboardRow();
        row.add(contactButton);

        keyboardMarkup.setKeyboard(Collections.singletonList(row));
        keyboardMarkup.setResizeKeyboard(true); // Уменьшает размер кнопок, чтобы они занимали меньше места
        keyboardMarkup.setOneTimeKeyboard(false); // Клавиатура остаётся видимой после нажатия кнопки
        keyboardMarkup.setSelective(true);
        return keyboardMarkup;
    }
}
