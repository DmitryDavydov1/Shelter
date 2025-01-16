package com.example.bot._for_shelter.command;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

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
        switch (messageText) {
            case "contact-information-button":
                // Запрашиваем у пользователя номер телефона
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId.toString());
                sendMessage.setText("Пожалуйста, введите номер телефона:");
                sendBotMessageService.sendMessage(sendMessage, messageId);
                break;

        }
    }


//    public void handleMessage(Update update) {
//
//    }
}

