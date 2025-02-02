package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.DTO.PetDTO;
import com.example.bot._for_shelter.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private PetDTO petDTO;
    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petDTO = new PetDTO();
        petDTO.setAge(3);
        petDTO.setGender("Male");
        petDTO.setWeight(10);
        petDTO.setNickname("Buddy");

        pet = new Pet();
        pet.setId(1L);
        pet.setAge(3);
        pet.setGender("Male");
        pet.setWeight(10);
        pet.setNickname("Buddy");
        pet.setHaveOwner(false);
    }

    @Test
    void testAddPet() {
        // Настройка мок-объекта для возврата сохраненного питомца
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // Выполнение тестируемого метода
        Pet savedPet = petService.addPet(petDTO);

        // Проверка значений сохраненного питомца
        assertNotNull(savedPet);
        assertEquals(petDTO.getAge(), savedPet.getAge());
        assertEquals(petDTO.getGender(), savedPet.getGender());
        assertEquals(petDTO.getWeight(), savedPet.getWeight());
        assertEquals(petDTO.getNickname(), savedPet.getNickname());
        assertFalse(savedPet.isHaveOwner());

        // Проверка вызова метода save
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    void testSetHaveOwner() {
        // Настройка мок-объекта для возврата найденного питомца
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Выполнение тестируемого метода
        petService.setHaveOwner(1L);

        // Проверка, что поле haveOwner установлено в true
        assertTrue(pet.isHaveOwner());

        // Проверка вызова методов findById и save
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testSetHaveOwner_PetNotFound_ShouldThrowException() {
        // Настройка мок-объекта для отсутствующего питомца
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Проверка выброса исключения
        Exception exception = assertThrows(NullPointerException.class, () -> {
            petService.setHaveOwner(1L);
        });

        // Проверка сообщения исключения
        assertEquals("Cannot invoke \"com.example.bot._for_shelter.model.Pet.setHaveOwner(boolean)\" because \"pet\" is null", exception.getMessage());

        // Проверка, что метод save не был вызван
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    void testSetHaveOwner_PetExists_ShouldSetHaveOwnerTrue() {
        // Настройка мок-объекта для успешного поиска питомца
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Выполнение тестируемого метода
        petService.setHaveOwner(1L);

        // Проверка, что поле haveOwner установлено в true
        assertTrue(pet.isHaveOwner());

        // Проверка вызова методов findById и save
        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(pet);
    }
}
