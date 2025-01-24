package com.example.bot._for_shelter.service;


import com.example.bot._for_shelter.command.*;
import com.example.bot._for_shelter.config.BotConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.writeContactAtBd;


@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig config;


    private final List<Command> commandList;

    public TelegramBot(BotConfig config, List<Command> commandList) {
        this.config = config;
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
        if (update.hasCallbackQuery()) {
            commandList.stream()
                    .filter(command -> command.isSupport(update.getCallbackQuery().getData()))
                    .forEach(command -> {
                        command.execute(update);
                    });
        }
        if (update.hasMessage() && update.getMessage().hasContact()) {
            commandList.stream()
                    .filter(command -> command.isSupport(writeContactAtBd.getCommandName()))
                    .forEach(command -> {
                        command.execute(update);
                    });
        }
    }
}




