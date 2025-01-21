package com.example.bot._for_shelter.command;

import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.example.bot._for_shelter.command.CommandName.*;

@Component
public class CommandContainer {

    @Autowired
    private WriteReportToBd writeReportToBd;


    private final HashMap<String, Command> commandMap;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        HashMap<String, Command> commandsMap = new HashMap<>();
        commandsMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandsMap.put(DOG.getCommandName(), new DogAndCatsCommand(sendBotMessageService));
        commandsMap.put(CAT.getCommandName(), new DogAndCatsCommand(sendBotMessageService));
        commandsMap.put(backToStartButton.getCommandName(), new StartCommand(sendBotMessageService));
        commandsMap.put(menuForInformation.getCommandName(), new MenuForInformationCommand(sendBotMessageService));
        commandsMap.put(contactData.getCommandName(), new ContactDataCommand(sendBotMessageService));
        commandsMap.put(WriteReportToBd1.getCommandName(), writeReportToBd);
        commandMap = commandsMap;
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }

}
