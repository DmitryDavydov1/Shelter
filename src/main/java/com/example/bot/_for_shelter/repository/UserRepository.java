package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link BotUser}.
 * Содержит методы для доступа и работы с данными пользователей бота.
 */
@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {

    /**
     * Находит пользователя по номеру телефона.
     *
     * @param s Номер телефона пользователя.
     * @return {@link BotUser}, соответствующий указанному номеру телефона.
     */
    BotUser findByPhoneNumber(String s);

    /**
     * Проверяет, существует ли пользователь с указанным chatId.
     *
     * @param chatId Идентификатор чата пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    boolean existsByChatId(String chatId);

    /**
     * Находит пользователя по chatId.
     *
     * @param chatId Идентификатор чата пользователя.
     * @return {@link BotUser}, соответствующий указанному chatId.
     */
    BotUser findByChatId(String chatId);
}
