package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Report}.
 * Содержит методы для доступа и работы с данными о отчетах, отправленных пользователями боту.
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * Находит отчет по идентификатору чата и наличию фотографии.
     *
     * @param s Идентификатор чата пользователя.
     * @param b Статус наличия фотографии в отчете: true — отчет содержит фотографию, false — не содержит.
     * @return {@link Report} с указанным chatId и состоянием наличия фотографии.
     */
    Report findByChatIdAndHavePhoto(String s, boolean b);

    /**
     * Проверяет, существует ли отчет с указанным chatId и состоянием наличия фотографии.
     *
     * @param s Идентификатор чата пользователя.
     * @param b Статус наличия фотографии в отчете.
     * @return true, если отчет существует, иначе false.
     */
    boolean existsByChatIdAndHavePhoto(String s, boolean b);
}
