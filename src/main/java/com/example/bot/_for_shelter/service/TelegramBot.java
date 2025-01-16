package com.example.bot._for_shelter.service;


import com.example.bot._for_shelter.command.CommandContainer;
import com.example.bot._for_shelter.command.SendBotMessageServiceImpl;
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

    private final CommandContainer commandContainer;

    public TelegramBot(BotConfig config) {
        this.config = config;

        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this));

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
            commandContainer.retrieveCommand(update.getMessage().getText()).execute(update);
        }
        if (update.hasCallbackQuery()) {
            commandContainer.retrieveCommand(update.getCallbackQuery().getData()).execute(update);
        }
    }
}



