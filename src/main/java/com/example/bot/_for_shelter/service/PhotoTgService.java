package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.PhotoTg;
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
}
