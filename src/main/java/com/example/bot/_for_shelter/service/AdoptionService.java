package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.AdoptionDTO;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {
    @Autowired
    AdoptionRepository adoptionRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    UserRepository userRepository;

    public Adoption addAdoption(AdoptionDTO adoptionDTO) {
        Adoption adoption = new Adoption();
        Pet pet = petRepository.findById(Long.valueOf(adoptionDTO.getPet_id()))
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with ID: " + adoptionDTO.getPet_id()));
        BotUser botUser = userRepository.findById(Long.valueOf(adoptionDTO.getPet_id()))
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + adoptionDTO.getBot_user_id()));
        adoption.setPet(pet);
        adoption.setBotUser(botUser);
        adoption.setLastDay(30);
        adoption.setCurrentDay(0);
        return adoptionRepository.save(adoption);
    }
}
