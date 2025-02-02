package com.example.bot._for_shelter.config;

import com.example.bot._for_shelter.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Класс BotInitializer отвечает за инициализацию и регистрацию бота при запуске приложения.
 * Использует Spring событие ContextRefreshedEvent для регистрации бота в момент инициализации контекста.
 */
@Component
public class BotInitializer {

    /**
     * Экземпляр TelegramBot, который будет зарегистрирован в TelegramBotsApi.
     */
    @Autowired
    TelegramBot telegramBot;

    /**
     * Метод, который слушает событие ContextRefreshedEvent, вызываемое после того, как
     * контекст приложения был полностью инициализирован.
     * Этот метод регистрирует бота в TelegramBotsApi, чтобы начать его работу.
     *
     * @throws TelegramApiException если произошла ошибка при регистрации бота в Telegram API
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException _) {
            // Логирование ошибки или обработка исключений можно добавить сюда
        }
    }
}
