package com.example.bot._for_shelter.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * Конфигурационный класс для настроек Telegram бота.
 * Загружает свойства из файла application.properties.
 */
@Getter
@Configuration
@PropertySource("application.properties")
@Data
public class BotConfig {

    /**
     * Имя бота, используемое для идентификации в Telegram.
     */
    @Value("${bot.name}")
    String botName;

    /**
     * Токен для доступа к Telegram Bot API.
     */
    @Value("${bot.token}")
    String token;
}
