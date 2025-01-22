package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }
}
