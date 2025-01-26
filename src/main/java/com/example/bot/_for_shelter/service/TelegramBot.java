package com.example.bot._for_shelter.service;


import com.example.bot._for_shelter.command.*;
import com.example.bot._for_shelter.config.BotConfig;


import com.example.bot._for_shelter.repository.UserRepository;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;


import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Comparator;
import java.util.List;

import static com.example.bot._for_shelter.command.CommandName.writeContactAtBd;


@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig config;

    private final StartCommand startCommand;
    private final List<Command> commandList;
    private final UserRepository userRepository;
    private final WriteReportToBd writeReportToBd;

    public TelegramBot(BotConfig config, StartCommand startCommand, List<Command> commandList, UserRepository userRepository, WriteReportToBd writeReportToBd) {
        this.config = config;
        this.startCommand = startCommand;
        this.commandList = commandList;
        this.userRepository = userRepository;
        this.writeReportToBd = writeReportToBd;
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
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            writeReportToBd.execute(update);
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().equals("/start")) {
                startCommand.execute(update);
                return;
            }

            String chatId = update.getMessage().getChatId().toString();
            String condition = userRepository.findByChatId(chatId).getCondition();
            if (!condition.equals("default")) {
                writeReportToBd.execute(update);

            }
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

    public void sendPhoto(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}




