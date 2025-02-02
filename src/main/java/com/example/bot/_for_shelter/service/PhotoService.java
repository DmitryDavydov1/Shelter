package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Photo;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.repository.PhotoRepository;
import com.example.bot._for_shelter.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Сервис для работы с фотографиями, связанными с отчетами.
 * Включает методы для загрузки фотографий и получения фотографий из базы данных.
 */
@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ReportRepository reportRepository;

    /**
     * Загружает фотографию и сохраняет ее в базе данных, связывая с отчетом.
     *
     * @param id ID отчета, к которому будет привязана фотография.
     * @param avatarFile Файл фотографии, который будет загружен.
     * @throws IOException Если произошла ошибка при чтении данных из файла.
     */
    public void uploadPhoto(Long id, MultipartFile avatarFile) throws IOException {
        Photo photo = new Photo();
        photo.setFileSize(avatarFile.getSize());
        photo.setMediaType(avatarFile.getContentType());
        photo.setPreview(avatarFile.getBytes());
        photo.setFileSize(avatarFile.getSize());
        Report report = reportRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Report not found"));
        photo.setReport(report);
        photoRepository.save(photo);
    }

    /**
     * Находит фотографию по ID отчета.
     *
     * @param id ID отчета, для которого требуется получить фотографию.
     * @return {@link Photo} объект, содержащий фотографию, связанную с отчетом.
     * @throws IllegalArgumentException Если отчет с таким ID не найден.
     */
    public Photo findAvatar(Long id) {
        return photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Photo not found for report ID: " + id));
    }
}
