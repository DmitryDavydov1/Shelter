package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Сущность, представляющая фотографию питомца.
 * Хранит информацию о фотографии, такую как размер файла, тип медиа,
 * превью изображения и связь с отчетом.
 */
@Setter
@Getter
@Entity
public class Photo {

    /**
     * Уникальный идентификатор фотографии.
     * Это значение генерируется автоматически при сохранении сущности в базе данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Размер файла фотографии.
     * Хранит размер файла фотографии в байтах.
     */
    private long fileSize;

    /**
     * Тип медиафайла.
     * Хранит MIME-тип файла (например, "image/jpeg").
     */
    private String mediaType;

    /**
     * Превью изображения.
     * Хранит данные изображения в формате byte[], которые представляют собой уменьшенную копию фото.
     * Используется для отображения миниатюры.
     */
    @Lob
    private byte[] preview;

    /**
     * Связь с отчетом.
     * Каждая фотография может быть связана с отчетом (например, для отслеживания состояния питомца).
     * Связь с сущностью Report (Отчет) через отношение One-to-One.
     */
    @OneToOne
    private Report report;
}
