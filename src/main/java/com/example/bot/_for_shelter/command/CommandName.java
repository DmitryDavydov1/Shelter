package com.example.bot._for_shelter.command;

public enum CommandName {

    START("/start"),
    CAT("dog-button"),
    DOG("cat-button"),
    takeAnimal("takeAnimal-button"),
    petReport("petReport-button"),
    callVolunteer("callVolunteer-button"),
    backMenuButton("back-menu-button");


    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
