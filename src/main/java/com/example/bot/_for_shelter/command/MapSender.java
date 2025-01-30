package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.bot._for_shelter.command.CommandName.MapButton;
import static com.example.bot._for_shelter.command.CommandName.SendWarning;

@Component
public class MapSender implements Command {


    private final TelegramBot bot;

    @Autowired
    @Lazy
    public MapSender(TelegramBot bot) {
        this.bot = bot;
    }


    @Override
    public void execute(Update update) {
        SendPhoto msg = SendPhoto
                .builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId().toString())
                .photo(new InputFile("https://avatars.mds.yandex.net/i?id=191beafe68bb99c9489949ee2eb7c95e4e6a8dce-5115981-images-thumbs&n=13-ExG2Jq2EgfoiWL5MiwSQEAAwIAA3kAAzYE"))
                .build();
        bot.sendPhoto(msg);
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(MapButton.getCommandName());
    }
}
