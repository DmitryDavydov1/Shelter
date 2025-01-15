package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.buttons.Buttons;
import com.example.bot._for_shelter.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    private Buttons buttons;

    public TelegramBot(BotConfig config, Buttons buttons) {
        this.config = config;
        this.buttons = buttons;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                try {
                    execute(buttons.startCommandReceived(chatId, update.getMessage().getChat().getFirstName()));
                } catch (TelegramApiException e) {
                    System.out.println(e);
                }
            } else {
                System.out.println("lsls");
            }

        } else if (update.hasCallbackQuery()) {
            var backButton = new InlineKeyboardButton();
            String backButtonButtonText = EmojiParser.parseToUnicode("Назад" + " :back:");
            backButton.setCallbackData("back-information-button");
            backButton.setText(backButtonButtonText);

            InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
            List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();

            rowInLine1.add(backButton);
            rowsInLine.add(rowInLine1);
            markupInLine.setKeyboard(rowsInLine);


            String callbackQuery = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId(); // Исправлено здесь

            EditMessageText message = new EditMessageText();
            message.setChatId(String.valueOf(chatId));

            if (callbackQuery.equals("dog-button")) {
                String text = "Выберите пункт";
                message.setText(text);
                message.setReplyMarkup(buttons.sendMenu(chatId));
            } else if (callbackQuery.equals("cat-button")) {
                String text = "Выберите пункт";
                buttons.sendMenu(chatId);
                message.setText(text);
                message.setReplyMarkup(buttons.sendMenu(chatId));
            }

            if (callbackQuery.equals("back-menu-button")) {
                SendMessage sendMessage = buttons.startCommandReceived(chatId, update.getCallbackQuery().getFrom().getFirstName());
                message.setText(sendMessage.getText());
                message.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getReplyMarkup());
            }

            message.setMessageId(messageId);

            if (callbackQuery.equals("information-button")) {
                SendMessage sendMessage = buttons.menuForInformation();
                message.setText(sendMessage.getText());
                message.setReplyMarkup((InlineKeyboardMarkup) sendMessage.getReplyMarkup());
            } else if (callbackQuery.equals("petReport-button")) {
                String answer = "Можешь прислать репорт";
                message.setText(answer);
            }

            if (callbackQuery.equals("back-information-button")) {
                message.setText("Выберите пункт");
                message.setReplyMarkup(buttons.sendMenu(chatId));
            }

            if (callbackQuery.equals("information-about-shelter-button")) {
                message.setText("Наш приют в астане");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println(e);
            }
        }
    }


}
