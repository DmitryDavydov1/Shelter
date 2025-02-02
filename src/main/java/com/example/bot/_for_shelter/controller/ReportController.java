package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.DTO.ReportDTO;
import com.example.bot._for_shelter.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с отчетами.
 * Этот контроллер предоставляет API для загрузки отчетов, содержащих информацию
 * о животных или событиях в приюте.
 */
@RestController
@RequestMapping(path = "report")
public class ReportController {

    /**
     * Сервис для обработки логики, связанной с отчетами.
     * Используется для добавления отчетов в систему.
     */
    @Autowired
    ReportService reportService;

    /**
     * Метод для добавления нового отчета.
     * Принимает данные отчета в виде объекта ReportDTO, который содержит информацию
     * о содержимом отчета, и передает их в сервис для обработки.
     *
     * @param reportDTO объект, содержащий данные отчета, переданные в теле запроса.
     * @return id нового объекта Report.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> addReport(ReportDTO reportDTO) {
        int reportId = reportService.addReport(reportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reportId);
    }
}
