package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.PhotoTg;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.repository.PhotoTgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoTgService {
    @Autowired
    PhotoTgRepository photoTgRepository;

    public void setsView(Long reportId) {
        PhotoTg photoTg = photoTgRepository.findById(reportId).orElse(null);
        photoTg.setViewed(true);
        photoTgRepository.save(photoTg);

    }

    public void addPhotoTg(String chat_id, String fId, Report report) {
        PhotoTg photoTg = new PhotoTg();
        photoTg.setChatId(chat_id);
        photoTg.setFileId(fId);
        photoTg.setReport(report);
        photoTg.setViewed(false);
        photoTgRepository.save(photoTg);
    }
}
