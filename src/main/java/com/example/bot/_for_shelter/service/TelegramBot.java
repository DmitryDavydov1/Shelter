package com.example.bot._for_shelter.service;

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

    public TelegramBot(BotConfig config) {
        this.config = config;
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

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    System.out.println("lsls");
            }
        } else if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            int messageId = update.getCallbackQuery().getMessage().getMessageId(); // Исправлено здесь

            EditMessageText message = new EditMessageText();
            message.setChatId(String.valueOf(chatId));

            if (callbackQuery.equals("dog-button")) {
                String answer = "we work with dog";
                message.setText(answer);
            } else if (callbackQuery.equals("cat-button")) {
                String answer = "we work with cat";
                message.setText(answer);
            }

            message.setMessageId(messageId); // И здесь

            try {
                execute(message);
            } catch (TelegramApiException e) {
                System.out.println(e);
            }

            sendMenu(chatId);
        }
    }


    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi " + name + ", nice to meet you";
        SendMessage message = sendMessage(chatId, answer);

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

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }


    }

    public SendMessage sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        return message;
    }

    public void sendMenu(long chatId) {
        String text = "Выберите пункт";
        SendMessage message = sendMessage(chatId, text);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine2 = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine3 = new ArrayList<>();
        var informationButton = new InlineKeyboardButton();
        String informationButtonText = EmojiParser.parseToUnicode("Узнать информацию о приюте" + " :information_source:");

        informationButton.setText(informationButtonText);
        informationButton.setCallbackData("information-button");

        var takeAnimalButton = new InlineKeyboardButton();
        String takeAnimalButtonText = EmojiParser.parseToUnicode("Как взять животное из приюта " + " :cat:");
        takeAnimalButton.setText(takeAnimalButtonText);
        takeAnimalButton.setCallbackData("takeAnimal-button");

        var petReportButtonButton = new InlineKeyboardButton();
        String petReportButtonText = EmojiParser.parseToUnicode("Прислать отчет о питомце" + " :dog:");
        petReportButtonButton.setText(petReportButtonText);
        petReportButtonButton.setCallbackData("petReport-button");

        rowInLine1.add(informationButton);
        rowInLine2.add(petReportButtonButton);
        rowInLine3.add(takeAnimalButton);
        rowsInLine.add(rowInLine1);
        rowsInLine.add(rowInLine2);
        rowsInLine.add(rowInLine3);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }


}
