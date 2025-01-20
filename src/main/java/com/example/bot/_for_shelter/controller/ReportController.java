package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.ReportDTO;
import com.example.bot._for_shelter.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addReport(ReportDTO reportDTO) {
        reportService.addReport(reportDTO);
    }
}
