package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.DTO.PetDTO;
import com.example.bot._for_shelter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с животными.
 * Этот контроллер предоставляет API для добавления информации о новом животном.
 */
@RestController
@RequestMapping(path = "pet")
public class PetController {

    /**
     * Сервис для обработки логики, связанной с животными.
     * Используется для добавления новых данных о животных в систему.
     */
    @Autowired
    PetService petService;

    /**
     * Метод для добавления нового животного.
     * Принимает данные о животном в формате JSON, преобразует их в объект PetDTO
     * и передает в сервис для обработки. Возвращает объект Pet, содержащий информацию
     * о добавленном животном.
     *
     * @param petDTO объект с данными животного, переданными в теле запроса.
     * @return объект Pet с информацией о добавленном животном.
     */
    @PostMapping
    public void addPet(@RequestBody PetDTO petDTO) {
        petService.addPet(petDTO);
    }
}
