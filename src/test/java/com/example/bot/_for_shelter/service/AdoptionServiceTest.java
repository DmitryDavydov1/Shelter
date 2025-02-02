package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.command.SendBotMessageService;
import com.example.bot._for_shelter.DTO.AdoptionDTO;
import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.repository.AdoptionRepository;
import com.example.bot._for_shelter.repository.PetRepository;
import com.example.bot._for_shelter.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SendBotMessageService sendBotMessageService;

    @InjectMocks
    private AdoptionService adoptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAdoptionSuccess() {
        Pet pet = new Pet();
        pet.setId(1L);

        BotUser botUser = new BotUser();
        botUser.setId(1L);

        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setPet_id(1L);
        adoptionDTO.setBot_user_id(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(userRepository.findById(1L)).thenReturn(Optional.of(botUser));
        when(adoptionRepository.save(any(Adoption.class))).thenAnswer(invocation -> {
            Adoption savedAdoption = invocation.getArgument(0);
            savedAdoption.setId(100L);
            return savedAdoption;
        });

        Long adoptionId = adoptionService.addAdoption(adoptionDTO);

        assertEquals(100L, adoptionId);
        verify(adoptionRepository, times(1)).save(any(Adoption.class));
    }

    @Test
    void testAddAdoptionPetNotFound() {
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setPet_id(1L);
        adoptionDTO.setBot_user_id(1L);


        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adoptionService.addAdoption(adoptionDTO));
        verify(adoptionRepository, never()).save(any(Adoption.class));
    }

    @Test
    void testAddAdoptionUserNotFound() {
        Pet pet = new Pet();
        pet.setId(1L);

        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setBot_user_id(1L);
        adoptionDTO.setPet_id(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adoptionService.addAdoption(adoptionDTO));
        verify(adoptionRepository, never()).save(any(Adoption.class));
    }

    @Test
    void testUserHaveAdoptionOrNo() {
        when(adoptionRepository.existsByBotUserId(1L)).thenReturn(true);

        assertTrue(adoptionService.userHaveAdoptionOrNo(1L));
        verify(adoptionRepository, times(1)).existsByBotUserId(1L);
    }

    @Test
    void testAddOneDay() {
        BotUser botUser = new BotUser();
        botUser.setChatId("1234567890");
        botUser.setPhoneNumber("+79961382430");

        Adoption adoption1 = new Adoption();
        adoption1.setId(1L);
        adoption1.setCurrentDay(29);
        adoption1.setLastDay(30);
        adoption1.setBotUser(botUser);

        when(adoptionRepository.findAll()).thenReturn(List.of(adoption1));
        when(userRepository.findByPhoneNumber("+79961382430")).thenReturn(botUser);

        adoptionService.addOneDay();

        // Проверяем, что метод сохранения вызван только один раз
        verify(adoptionRepository, times(1)).save(any(Adoption.class));
        assertEquals(30, adoption1.getCurrentDay());
    }




    @Test
    void testFindByChatId() {
        BotUser botUser = new BotUser();
        botUser.setChatId("1234567890");

        Adoption adoption = new Adoption();
        adoption.setBotUser(botUser);

        when(adoptionRepository.findAll()).thenReturn(List.of(adoption));

        assertTrue(adoptionService.findByChatId(1234567890L));
        verify(adoptionRepository, times(1)).findAll();
    }

    @Test
    void testButtonForAdoptionWith30Days() {
        InlineKeyboardMarkup markup = adoptionService.buttonForAdoptionWith30Days(1L);
        assertNotNull(markup);
        assertEquals(4, markup.getKeyboard().size());
    }

    @Test
    void testButtonForAdoptionWith44Or60Days() {
        InlineKeyboardMarkup markup = adoptionService.buttonForAdoptionWith44Or60Days(1L);
        assertNotNull(markup);
        assertEquals(2, markup.getKeyboard().size());
    }
}
