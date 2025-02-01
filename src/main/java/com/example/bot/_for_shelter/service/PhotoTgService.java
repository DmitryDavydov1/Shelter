package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.PhotoTg;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.repository.PhotoTgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с фотографиями, отправленными через Telegram.
 * Включает методы для добавления фотографий и изменения их статуса просмотра.
 */
@Service
public class PhotoTgService {
    @Autowired
    PhotoTgRepository photoTgRepository;

    /**
     * Устанавливает статус фотографии как "просмотрено".
     *
     * @param reportId ID отчета, связанного с фотографией.
     */
    public void setsView(Long reportId) {
        PhotoTg photoTg = photoTgRepository.findById(reportId).orElse(null);
        if (photoTg != null) {
            photoTg.setViewed(true);
            photoTgRepository.save(photoTg);
        }
    }

    /**
     * Добавляет фотографию из Telegram, связывая ее с отчетом.
     *
     * @param chat_id ID чата Telegram, в котором была отправлена фотография.
     * @param fId ID файла в Telegram.
     * @param report Объект отчета, к которому будет привязана фотография.
     */
    public void addPhotoTg(String chat_id, String fId, Report report) {
        PhotoTg photoTg = new PhotoTg();
        photoTg.setChatId(chat_id);
        photoTg.setFileId(fId);
        photoTg.setReport(report);
        photoTg.setViewed(false);
        photoTgRepository.save(photoTg);
    }
}
