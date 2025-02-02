package com.example.bot._for_shelter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) для передачи данных о питомце.
 * Используется для обмена данными между слоями приложения, например, при создании
 * или обновлении информации о питомце в базе данных.
 */
@Setter
@Getter
public class PetDTO {

    /**
     * Кличка питомца.
     * Хранит имя или прозвище питомца, которое будет использоваться в системе.
     */
    private String nickname;

    /**
     * Возраст питомца.
     * Хранит возраст питомца в годах.
     */
    private int age;

    /**
     * Вес питомца.
     * Хранит вес питомца в килограммах.
     */
    private int weight;

    /**
     * Пол питомца.
     * Хранит информацию о поле питомца (мужской или женский).
     */
    private String gender;

    /**
     * Рост питомца.
     * Хранит рост питомца в сантиметрах.
     */
    private int height;
}
