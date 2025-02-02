package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Сущность, представляющая отчет, отправляемый пользователем боту.
 * Хранит информацию о тексте отчета, идентификаторе чата, времени отправки,
 * статусе просмотра и наличии фотографии.
 */
@Setter
@Getter
@Entity
public class Report {

    /**
     * Уникальный идентификатор отчета.
     * Это значение генерируется автоматически при сохранении сущности в базе данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Текст отчета.
     * Хранит текстовое описание отчета, которое было отправлено пользователем.
     */
    private String text;

    /**
     * Идентификатор чата пользователя, отправившего отчет.
     * Хранит уникальный идентификатор чата пользователя, который отправил отчет.
     */
    private String chatId;

    /**
     * Время отправки отчета.
     * Хранит время, когда отчет был отправлен, с использованием типа LocalTime.
     */
    private LocalTime time;

    /**
     * Статус просмотра отчета.
     * Указывает, был ли отчет просмотрен (true — просмотрен, false — не просмотрен).
     */
    private boolean viewed;

    /**
     * Наличие фотографии в отчете.
     * Указывает, прикреплена ли фотография к отчету (true — есть, false — нет).
     */
    private boolean havePhoto;
}
