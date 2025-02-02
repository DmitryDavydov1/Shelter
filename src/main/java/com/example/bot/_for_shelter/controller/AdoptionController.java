package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.DTO.AdoptionDTO;
import com.example.bot._for_shelter.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с усыновлением животных.
 * Этот контроллер предоставляет API для добавления новых заявок на усыновление.
 */
@RestController
@RequestMapping(path = "adoption")
public class AdoptionController {

    /**
     * Сервис для обработки логики, связанной с усыновлением.
     * Используется для добавления данных о заявках на усыновление.
     */
    @Autowired
    private AdoptionService adoptionService;

    /**
     * Метод для добавления новой заявки на усыновление.
     * Принимает данные заявки в формате JSON, преобразует их в объект AdoptionDTO
     * и передает в сервис для обработки. Возвращает объект Adoption, содержащий информацию
     * о созданной заявке.
     *
     * @param adoptionDTO объект с данными заявки на усыновление, переданными в теле запроса.
     * @return объект Adoption с информацией о созданной заявке на усыновление.
     */
    @PostMapping
    public Adoption addAdoptiоn(@RequestBody AdoptionDTO adoptionDTO) {
        return adoptionService.addAdoption(adoptionDTO);
    }
}
