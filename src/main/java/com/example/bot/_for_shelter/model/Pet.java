package com.example.bot._for_shelter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Сущность, представляющая животное в приюте.
 * Хранит информацию о питомце, такую как его кличка, возраст, вес, пол, рост
 * и наличие владельца.
 */
@Setter
@Getter
@Entity
public class Pet {

    /**
     * Уникальный идентификатор животного.
     * Это значение генерируется автоматически при сохранении сущности в базе данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Кличка животного.
     * Хранит имя или прозвище животного.
     */
    private String nickname;

    /**
     * Возраст животного.
     * Хранит возраст животного в годах.
     */
    private int age;

    /**
     * Вес животного.
     * Хранит вес животного в килограммах.
     */
    private int weight;

    /**
     * Пол животного.
     * Хранит информацию о поле животного (мужской или женский).
     */
    private String gender;

    /**
     * Рост животного.
     * Хранит рост животного в сантиметрах.
     */
    private int height;

    /**
     * Наличие владельца.
     * Указывает, есть ли у животного владелец (true - есть, false - нет).
     */
    private boolean haveOwner;

    /**
     * Конструктор по умолчанию для создания объекта Pet.
     * Обычно используется для создания пустого объекта, который будет заполнен позже.
     */
    public Pet() {
    }

    public boolean getHaveOwner() {
        return haveOwner;
    }
}
