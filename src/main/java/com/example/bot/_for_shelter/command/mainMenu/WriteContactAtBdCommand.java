package com.example.bot._for_shelter.command.mainMenu;

import com.example.bot._for_shelter.DTO.BotUserDTO;
import com.example.bot._for_shelter.command.Command;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.bot._for_shelter.command.CommandName.*;

@Component
public class WriteContactAtBdCommand implements Command {



    private final UserService userService;

    public WriteContactAtBdCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(Update update) {
        Contact contact = update.getMessage().getContact();
        String name = contact.getFirstName();
        String phoneNumber = contact.getPhoneNumber();
        BotUserDTO botUserDTO = new BotUserDTO();
        botUserDTO.setChatId(String.valueOf(update.getMessage().getChatId()));
        botUserDTO.setName(name);
        botUserDTO.setPhoneNumber(phoneNumber);
        userService.addUser(botUserDTO);
    }

    @Override
    public boolean isSupport(String command) {
        return command.equals(writeContactAtBd.getCommandName());
    }
}
