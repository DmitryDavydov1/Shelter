package com.example.bot._for_shelter.command;

public enum CommandName {

    START("/start"),
    CAT("dog-button"),
    DOG("cat-button"),
    takeAnimal("takeAnimal-button"),
    petReport("petReport-button"),
    callVolunteer("callVolunteer-button"),
    backToStartButton("back-to-start-button"),
    menuForInformation("information-button"),
    contactData("contact-information-button"),
    writeContactAtBd("writeContactAtBd"),
    WriteReportToBd1("petReport-button"),
    takeAnimalFromShelter("/takeAnimal");


    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
