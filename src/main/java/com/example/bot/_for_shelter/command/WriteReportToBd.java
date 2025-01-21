package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class WriteReportToBd implements Command {


    @Autowired
    private UserService userService;

    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            userService.changeCondition(chatId, "report");
        }
    }
}
