package com.example.bot._for_shelter.service;


import com.example.bot._for_shelter.command.*;
import com.example.bot._for_shelter.config.BotConfig;

import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    UserService userService;


    final BotConfig config;

    //    private final CommandContainer commandContainer;
    private final List<Command> commandList;

    public TelegramBot(BotConfig config, List<Command> commandList) {
        this.config = config;
//        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), userService);

        this.commandList = commandList;
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
            commandList.stream()
                    .filter(command -> command.isSupport(update.getMessage().getText()))
                    .forEach(command -> {
                        command.execute(update);
                    });
        }
//        if (update.hasCallbackQuery()) {
//            commandList.retrieveCommand(update.getCallbackQuery().getData()).execute(update);
//        }
//        if (update.hasMessage() && update.getMessage().hasContact()) {
//            new WriteContactAtBdCommand(userService).execute(update);
//        }
    }
}



