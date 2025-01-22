package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.service.PetService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public Pet addPet(@RequestBody Pet pet) {
        return petService.addPet(pet);
    }
}
