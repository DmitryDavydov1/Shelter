package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.model.ReportDTO;
import com.example.bot._for_shelter.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalTime;
@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;


    public void addReport(ReportDTO reportDTO) {
        Report report = new Report();
        report.setText(reportDTO.getText());
        report.setChatId(reportDTO.getChatId());
        report.setViewed(false);
        report.setTime(LocalTime.now());
        reportRepository.save(report);
    }
}
