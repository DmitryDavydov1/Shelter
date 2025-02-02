package com.example.bot._for_shelter.command;

/**
 * Перечисление доступных команд бота и их соответствующих идентификаторов.
 */
public enum CommandName {

    /** Команда для старта взаимодействия пользователя. */
    START("user-button"),

    /** Команда для получения информации о кошках. */
    CAT("dog-button"),

    /** Команда для получения информации о собаках. */
    DOG("cat-button"),

    /** Команда для взятия животного из приюта. */
    takeAnimal("takeAnimal-button"),

    /** Команда для отправки отчета о животном. */
    petReport("petReport-button"),

    /** Команда для вызова волонтера. */
    callVolunteer("callVolunteer-button"),

    /** Кнопка возврата к стартовому меню. */
    backToStartButton("back-to-start-button"),

    /** Команда для получения информационного меню. */
    menuForInformation("information-button"),

    /** Команда для предоставления контактных данных. */
    contactData("contact-information-button"),

    /** Команда для записи контактных данных в базу данных. */
    writeContactAtBd("writeContactAtBd"),

    /** Команда для записи отчета о питомце в базу данных. */
    WriteReportToBd1("petReport-button"),

    /** Команда для взятия животного из приюта. */
    takeAnimalFromShelter("/takeAnimal"),

    /** Команда для выбора роли администратора или пользователя. */
    AdminOrUser("/start"),

    /** Команда для просмотра отчетов администратором. */
    WatchReportsByAdmin("/watchCommand"),

    /** Команда для пометки отчета как просмотренного. */
    ReportWasViewed("report-was-viewed"),

    /** Команда для отправки предупреждения пользователю. */
    SendWarning("send-warning"),

    /** Команда для получения карты. */
    MapButton("map-button");

    private final String commandName;

    /**
     * Конструктор перечисления.
     *
     * @param commandName текстовое представление команды.
     */
    CommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Возвращает текстовое представление команды.
     *
     * @return текстовое имя команды.
     */
    public String getCommandName() {
        return commandName;
    }
}
