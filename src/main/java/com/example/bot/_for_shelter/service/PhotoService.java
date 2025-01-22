package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Photo;
import com.example.bot._for_shelter.model.Report;
import com.example.bot._for_shelter.repository.PhotoRepository;
import com.example.bot._for_shelter.repository.ReportRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ReportRepository reportRepository;

    public void uploadPhoto(Long id, MultipartFile avatarFile) throws IOException {
        Photo photo = new Photo();
        photo.setFileSize(avatarFile.getSize());
        photo.setMediaType(avatarFile.getContentType());
        photo.setPreview(avatarFile.getBytes());
        photo.setFileSize(avatarFile.getSize());
        Report report = reportRepository.findById(id).get();
        photo.setReport(report);
        photoRepository.save(photo);

    }

    public Photo findAvatar(Long id) {
        return photoRepository.findById(id).get();
    }
}
