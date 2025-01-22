package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Photo;
import com.example.bot._for_shelter.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "photo")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PhotoController {
    @Autowired
    PhotoService photoService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addPhoto(Long id, @RequestParam MultipartFile avatar) throws IOException {
        photoService.uploadPhoto(id, avatar);
    }

    @GetMapping(value = "/{id}/photo-from-db")
    public HttpEntity<byte[]> getPhoto(@PathVariable Long id) {
        Photo photo = photoService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getPreview().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photo.getPreview());
    }

}
