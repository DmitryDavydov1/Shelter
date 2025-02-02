package com.example.bot._for_shelter.DTO;

/**
 * DTO (Data Transfer Object) для передачи данных о пользователе бота.
 * Используется для обмена данными между слоями приложения, например, при создании
 * или обновлении информации о пользователе в базе данных.
 */
public class BotUserDTO {

    /**
     * Идентификатор чата пользователя.
     * Представляет уникальный идентификатор чата пользователя в системе.
     */
    private String chatId;

    /**
     * Имя пользователя.
     * Хранит имя пользователя, которое будет использоваться в коммуникации с ним.
     */
    private String name;

    /**
     * Номер телефона пользователя.
     * Хранит контактный номер телефона пользователя, если он был предоставлен.
     */
    private String phoneNumber;

    /**
     * Получает идентификатор чата пользователя.
     * @return идентификатор чата пользователя.
     */
    public String getChatId() {
        return chatId;
    }

    /**
     * Устанавливает идентификатор чата пользователя.
     * @param chatId уникальный идентификатор чата пользователя.
     */
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    /**
     * Получает номер телефона пользователя.
     * @return номер телефона пользователя.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Устанавливает номер телефона пользователя.
     * @param phoneNumber контактный номер телефона пользователя.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Получает имя пользователя.
     * @return имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     * @param name имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }
}
