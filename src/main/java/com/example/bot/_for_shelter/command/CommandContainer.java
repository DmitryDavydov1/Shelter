package com.example.bot._for_shelter.command;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;

import static com.example.bot._for_shelter.command.CommandName.*;

public class CommandContainer {

    private final HashMap<String, Command> commandMap;

    public CommandContainer(SendBotMessageService sendBotMessageService) {
        HashMap<String, Command> commandsMap = new HashMap<>();
        commandsMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandsMap.put(DOG.getCommandName(), new DogAndCatsCommand(sendBotMessageService));
        commandsMap.put(CAT.getCommandName(), new DogAndCatsCommand(sendBotMessageService));
        commandsMap.put(backToStartButton.getCommandName(), new StartCommand(sendBotMessageService));
        commandMap = commandsMap;
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.get(commandIdentifier);
    }
}
