package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private BotUserDTO botUserDTO;
    private BotUser botUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        botUserDTO = new BotUserDTO();
        botUserDTO.setName("John Doe");
        botUserDTO.setChatId("12345");
        botUserDTO.setPhoneNumber("1234567890");

        botUser = new BotUser();
        botUser.setName("John Doe");
        botUser.setChatId("12345");
        botUser.setPhoneNumber("1234567890");
        botUser.setCondition("default");
    }

    @Test
    void testAddUser_NewUser() {
        // Настройка моков
        when(userRepository.existsByChatId("12345")).thenReturn(false);

        // Выполнение тестируемого метода
        BotUser savedUser = userService.addUser(botUserDTO);

        // Проверка значений
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("12345", savedUser.getChatId());
        assertEquals("1234567890", savedUser.getPhoneNumber());
        assertEquals("default", savedUser.getCondition());

        // Проверка вызова save
        verify(userRepository, times(1)).save(any(BotUser.class));
    }

    @Test
    void testAddUser_ExistingUser() {
        // Настройка моков для существующего пользователя
        when(userRepository.existsByChatId("12345")).thenReturn(true);

        // Выполнение тестируемого метода
        BotUser resultUser = userService.addUser(botUserDTO);

        // Проверка, что сохранение не произошло
        verify(userRepository, never()).save(any(BotUser.class));

        // Проверка значений возвращенного пользователя
        assertNotNull(resultUser);
        assertEquals("John Doe", resultUser.getName());
    }

    @Test
    void testChangeCondition_UserExists() {
        // Настройка моков для существующего пользователя
        when(userRepository.findByChatId("12345")).thenReturn(botUser);

        // Выполнение тестируемого метода
        userService.changeCondition(12345L, "new-condition");

        // Проверка, что условие было изменено и сохранено
        assertEquals("new-condition", botUser.getCondition());
        verify(userRepository, times(1)).save(botUser);
    }


    @Test
    void testCondition_UserExists() {
        // Настройка для существующего пользователя
        when(userRepository.findByChatId("12345")).thenReturn(botUser);

        // Выполнение тестируемого метода
        String condition = userService.condition(12345L);

        // Проверка значений
        assertEquals("default", condition);
    }

    @Test
    void testChangeCondition_UserNotFound_ShouldThrowException() {
        // Настройка для отсутствующего пользователя
        when(userRepository.findByChatId("12345")).thenReturn(null);

        // Проверка выброса исключения
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.changeCondition(12345L, "new-condition");
        });

        assertEquals("Cannot invoke \"com.example.bot._for_shelter.model.BotUser.setCondition(String)\" because \"user\" is null", exception.getMessage());
        verify(userRepository, never()).save(any(BotUser.class));
    }

    @Test
    void testCondition_UserNotFound_ShouldThrowException() {
        // Настройка для отсутствующего пользователя
        when(userRepository.findByChatId("12345")).thenReturn(null);

        // Проверка выброса исключения
        Exception exception = assertThrows(NullPointerException.class, () -> {
            userService.condition(12345L);
        });

        assertEquals("Cannot invoke \"com.example.bot._for_shelter.model.BotUser.getCondition()\" because the return value of \"com.example.bot._for_shelter.repository.UserRepository.findByChatId(String)\" is null", exception.getMessage());
    }

}
