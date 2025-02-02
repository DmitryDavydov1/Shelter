package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Репозиторий для работы с сущностью {@link Adoption}.
 * Содержит методы для доступа к данным о принятии животного, а также для выполнения
 * операций с базой данных, таких как проверка существования записи и обновление информации.
 */
@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Integer> {

    /**
     * Проверяет, существует ли запись о принятии животного для пользователя с указанным ID.
     *
     * @param userId Идентификатор пользователя.
     * @return true, если запись существует, иначе false.
     */
    boolean existsByBotUserId(Long userId);

    /**
     * Обновляет поле {@code currentDay} для всех записей в таблице Adoption.
     * Выполняет операцию добавления одного дня в поле {@code currentDay} для всех записей.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Adoption a SET a.currentDay = a.currentDay")
    void addOneDay();
}
