package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.PhotoTg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link PhotoTg}.
 * Содержит методы для доступа и работы с данными о фотографиях, полученных через Telegram.
 */
@Repository
public interface PhotoTgRepository extends JpaRepository<PhotoTg, Long> {

    /**
     * Находит все фотографии, у которых установлен указанный статус просмотра.
     *
     * @param b Статус просмотра: true — фотография была просмотрена, false — не была просмотрена.
     * @return Список фотографий с указанным статусом {@code viewed}.
     */
    List<PhotoTg> findAllByViewed(boolean b);
}
