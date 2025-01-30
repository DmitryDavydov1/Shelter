package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.AdoptionDTO;
import com.example.bot._for_shelter.service.AdoptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "adoption")
public class AdoptionController {
    @Autowired
    private AdoptionService adoptionService;

    @PostMapping
    public Adoption addAdoptiоn(@RequestBody AdoptionDTO adoptionDTO) {
        return adoptionService.addAdoption(adoptionDTO);
    }
}
