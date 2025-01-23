package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.bot._for_shelter.command.CommandName.WriteReportToBd1;
import static com.example.bot._for_shelter.command.CommandName.writeContactAtBd;

@Component
public class WriteReportToBd implements Command {

    private final UserService userService;

    public WriteReportToBd(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void execute(Update update) {
        if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            userService.changeCondition(chatId, "report");
        }
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(WriteReportToBd1.getCommandName());
    }
}
