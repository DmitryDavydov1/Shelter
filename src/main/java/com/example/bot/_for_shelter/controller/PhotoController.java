package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Photo;
import com.example.bot._for_shelter.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Контроллер для обработки запросов, связанных с фотографиями животных.
 * Этот контроллер предоставляет API для загрузки фотографий и получения изображений
 * из базы данных.
 */
@RestController
@RequestMapping(path = "photo")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class PhotoController {

    /**
     * Сервис для обработки логики, связанной с фотографиями.
     * Используется для загрузки и извлечения фотографий животных.
     */
    @Autowired
    PhotoService photoService;

    /**
     * Метод для загрузки фотографии (аватара) для животного.
     * Принимает данные фотографии в виде multipart-запроса и сохраняет их
     * в базе данных, ассоциируя с животным по его идентификатору.
     *
     * @param id     идентификатор животного, для которого загружается фотография.
     * @param avatar файл изображения, который необходимо загрузить.
     * @return id нового объекта Photo.
     * @throws IOException если произошла ошибка при обработке файла.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> addPhoto(Long id, @RequestParam MultipartFile avatar) throws IOException {
        Long photoId = photoService.uploadPhoto(id, avatar);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoId);
    }

    /**
     * Метод для получения фотографии животного из базы данных.
     * По заданному идентификатору животного извлекает фотографию и возвращает
     * ее в виде байтового массива с соответствующими заголовками.
     *
     * @param id идентификатор животного, для которого требуется получить фотографию.
     * @return HTTP-ответ с фотографией животного в виде байтового массива.
     */
    @GetMapping(value = "/{id}/photo-from-db")
    public HttpEntity<byte[]> getPhoto(@PathVariable Long id) {
        Photo photo = photoService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getPreview().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photo.getPreview());
    }

}
