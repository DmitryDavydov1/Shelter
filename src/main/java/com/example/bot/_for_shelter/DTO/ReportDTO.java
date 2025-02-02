package com.example.bot._for_shelter.DTO;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для передачи данных отчета.
 * Используется для обмена данными между слоями приложения, например, при создании
 * или обновлении отчета.
 */
@Setter
@Getter
public class ReportDTO {

    /**
     * Текст отчета.
     * Хранит описание отчета, которое будет отправлено пользователем.
     */
    private String text;

    /**
     * Идентификатор чата пользователя, отправившего отчет.
     * Хранит уникальный идентификатор чата, в котором был отправлен отчет.
     */
    private String chatId;
}
