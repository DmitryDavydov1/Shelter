package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность, представляющая заявку на усыновление животного.
 * Хранит информацию о заявке на усыновление, включая данные о животном,
 * пользователе бота и периоде усыновления.
 */
@Entity
@Setter
@Getter
public class Adoption {

    /**
     * Уникальный идентификатор заявки на усыновление.
     * Это значение генерируется автоматически при сохранении сущности в базе данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Животное, которое подлежит усыновлению.
     * Связь с сущностью Pet (Животное) через отношение One-to-One.
     */
    @OneToOne
    private Pet pet;

    /**
     * Пользователь бота, подавший заявку на усыновление.
     * Связь с сущностью BotUser (Пользователь бота) через отношение One-to-One.
     */
    @OneToOne
    private BotUser botUser;

    /**
     * Текущий день усыновления.
     * Хранит информацию о том, на какой день с момента начала усыновления находится заявка.
     */
    private Integer currentDay;

    /**
     * Последний день усыновления.
     * Хранит информацию о конечной дате усыновления животного.
     */
    private Integer lastDay;
}
