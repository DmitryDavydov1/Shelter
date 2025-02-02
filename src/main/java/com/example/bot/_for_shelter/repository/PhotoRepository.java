package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Photo}.
 * Содержит методы для доступа и работы с данными о фотографиях питомцев,
 * сохраненными в базе данных.
 */
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Репозиторий наследует все базовые методы от JpaRepository для работы с сущностью Photo
}
