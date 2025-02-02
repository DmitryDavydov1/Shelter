package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.DTO.AdoptionDTO;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdoptionService adoptionService;

    private AdoptionDTO adoptionDTO;
    private Pet pet;
    private BotUser botUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adoptionDTO = new AdoptionDTO();
        adoptionDTO.setPet_id(1L);
        adoptionDTO.setBot_user_id(2L);

        pet = new Pet();
        pet.setId(1L);

        botUser = new BotUser();
        botUser.setId(2L);
    }

//    @Test
//    void testAddAdoption_Success() {
//        when(petRepository.findById(1L)).thenReturn(java.util.Optional.of(pet));
//        when(userRepository.findById(2L)).thenReturn(java.util.Optional.of(botUser));
//        when(adoptionRepository.save(any(Adoption.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        Adoption adoption = adoptionService.addAdoption(adoptionDTO);
//
//        assertNotNull(adoption);
//        assertEquals(pet, adoption.getPet());
//        assertEquals(botUser, adoption.getBotUser());
//        assertEquals(30, adoption.getLastDay());
//        assertEquals(0, adoption.getCurrentDay());
//
//        verify(petRepository, times(1)).findById(1L);
//        verify(userRepository, times(1)).findById(2L);
//        verify(adoptionRepository, times(1)).save(adoption);
//    }

    @Test
    void testAddAdoption_PetNotFound() {
        when(petRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> adoptionService.addAdoption(adoptionDTO));

        assertEquals("Pet not found with ID: 1", exception.getMessage());
        verify(userRepository, never()).findById(anyLong());
        verify(adoptionRepository, never()).save(any(Adoption.class));
    }

    @Test
    void testAddAdoption_UserNotFound() {
        when(petRepository.findById(1L)).thenReturn(java.util.Optional.of(pet));
        when(userRepository.findById(2L)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> adoptionService.addAdoption(adoptionDTO));

        assertEquals("User not found with ID: 2", exception.getMessage());
        verify(adoptionRepository, never()).save(any(Adoption.class));
    }

    @Test
    void testUserHaveAdoptionOrNo_UserHasAdoption() {
        when(adoptionRepository.existsByBotUserId(2L)).thenReturn(true);

        boolean result = adoptionService.userHaveAdoptionOrNo(2L);

        assertTrue(result);
        verify(adoptionRepository, times(1)).existsByBotUserId(2L);
    }

    @Test
    void testUserHaveAdoptionOrNo_UserDoesNotHaveAdoption() {
        when(adoptionRepository.existsByBotUserId(2L)).thenReturn(false);

        boolean result = adoptionService.userHaveAdoptionOrNo(2L);

        assertFalse(result);
        verify(adoptionRepository, times(1)).existsByBotUserId(2L);
    }
}
