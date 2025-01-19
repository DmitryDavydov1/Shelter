package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;

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
}
