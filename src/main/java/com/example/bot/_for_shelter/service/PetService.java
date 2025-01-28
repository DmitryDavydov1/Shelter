package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.model.PetDTO;
import com.example.bot._for_shelter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    public Pet addPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setAge(petDTO.getAge());
        pet.setGender(petDTO.getGender());
        pet.setWeight(petDTO.getWeight());
        pet.setNickname(petDTO.getNickname());
        pet.setHaveOwner(false);
        return petRepository.save(pet);
    }

    public void setHaveOwner(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        pet.setHaveOwner(true);
        petRepository.save(pet);
    }
}
