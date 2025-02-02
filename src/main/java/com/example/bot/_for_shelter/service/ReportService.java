package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.DTO.ReportDTO;
import com.example.bot._for_shelter.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

/**
 * Сервис для работы с отчетами, включая добавление новых отчетов в базу данных.
 */
@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;

    /**
     * Добавляет новый отчет в базу данных.
     * Заполняет поля отчета, включая текст, идентификатор чата, текущее время и статус просмотра.
     * Изначально устанавливается статус без фотографии.
     *
     * @param reportDTO объект, содержащий данные для создания отчета.
     */
    public void addReport(ReportDTO reportDTO) {
        Report report = new Report();
        report.setText(reportDTO.getText());
        report.setChatId(reportDTO.getChatId());
        report.setViewed(false);
        report.setTime(LocalTime.now());
        report.setHavePhoto(false);
        reportRepository.save(report);
    }
}
