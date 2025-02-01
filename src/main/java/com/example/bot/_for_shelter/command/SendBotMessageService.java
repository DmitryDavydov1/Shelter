package com.example.bot._for_shelter.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

/**
 * Интерфейс для сервиса отправки сообщений и медиа в Telegram.
 */
public interface SendBotMessageService {

    /**
     * Отправляет текстовое сообщение в указанный чат.
     *
     * @param message объект сообщения для отправки.
     * @param chatId идентификатор чата, в который отправляется сообщение.
     */
    void sendMessage(SendMessage message, Integer chatId);

    /**
     * Отправляет текстовое сообщение в указанный чат с дополнительной информацией о команде.
     *
     * @param message объект сообщения для отправки.
     * @param chatId идентификатор чата, в который отправляется сообщение.
     * @param command команда, связанная с сообщением.
     */
    void sendMessage(SendMessage message, Integer chatId, String command);

    /**
     * Отправляет сообщение с прикрепленной клавиатурой.
     *
     * @param message объект сообщения с клавиатурой.
     */
    void sendMessageWithKeyboardMarkup(SendMessage message);

    /**
     * Отправляет фотографию в чат.
     *
     * @param sendPhoto объект с параметрами фотографии для отправки.
     */
    void sendPhoto(SendPhoto sendPhoto);
}
