package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.model.ReportDTO;
import com.example.bot._for_shelter.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

public class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    private ReportDTO reportDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание объекта ReportDTO для теста
        reportDTO = new ReportDTO();
        reportDTO.setText("Test report");
        reportDTO.setChatId(String.valueOf(123L));
    }

    @Test
    void testAddReport() {
        // Выполнение метода, который нужно протестировать
        reportService.addReport(reportDTO);

        // Проверка, что метод save был вызван один раз с объектом Report
        verify(reportRepository, times(1)).save(any(Report.class));

        // Проверка, что свойства объекта Report были установлены правильно
        // Можете добавить проверки на setText, setChatId и другие поля, если необходимо.
    }

    @Test
    void testAddReport_withDefaults() {
        // Выполнение метода addReport
        reportService.addReport(reportDTO);

        // Проверка значений объекта, переданного в репозиторий
        ArgumentCaptor<Report> captor = ArgumentCaptor.forClass(Report.class);
        verify(reportRepository, times(1)).save(captor.capture());

        Report savedReport = captor.getValue();

        // Проверка свойств объекта Report
        assertEquals(reportDTO.getText(), savedReport.getText(), "Текст отчета должен совпадать");
        assertEquals(reportDTO.getChatId(), savedReport.getChatId(), "ChatId должен совпадать");
        assertFalse(savedReport.isViewed(), "Отчет должен быть не просмотренным");
        assertFalse(savedReport.isHavePhoto(), "Отчет не должен содержать фото");
        assertNotNull(savedReport.getTime(), "Время отчета должно быть установлено");
    }

}
