package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.DTO.PetDTO;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @InjectMocks
    private PetService petService;

    @Mock
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPet() {
        PetDTO petDTO = new PetDTO();
        petDTO.setAge(3);
        petDTO.setGender("Male");
        petDTO.setWeight(15);
        petDTO.setNickname("Buddy");

        Pet savedPet = new Pet();
        savedPet.setId(1L);

        // Настройка возвращаемого значения при вызове save
        when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> {
            Pet pet = invocation.getArgument(0);
            pet.setId(1L); // Симулируем установку ID
            return pet;
        });

        Long petId = petService.addPet(petDTO);

        assertEquals(1L, petId);
        verify(petRepository, times(1)).save(any(Pet.class));
    }


    @Test
    void testSetHaveOwner() {
        Long petId = 1L;
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setHaveOwner(false);

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        petService.setHaveOwner(petId);

        assertTrue(pet.getHaveOwner());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testSetHaveOwner_PetNotFound() {
        Long petId = 1L;

        when(petRepository.findById(petId)).thenReturn(Optional.empty());

        petService.setHaveOwner(petId);

        verify(petRepository, never()).save(any(Pet.class));
    }
}
