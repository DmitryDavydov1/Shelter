package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet findAllById(int petId);
}
