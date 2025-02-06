package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.DTO.BotUserDTO;
import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        BotUserDTO botUserDTO = new BotUserDTO();
        botUserDTO.setName("John Doe");
        botUserDTO.setChatId("12345");
        botUserDTO.setPhoneNumber("+79991234567");

        BotUser savedBotUser = new BotUser();
        savedBotUser.setId(1L);

        when(userRepository.existsByChatId(botUserDTO.getChatId())).thenReturn(false);
        when(userRepository.save(any(BotUser.class))).thenAnswer(invocation -> {
            BotUser user = invocation.getArgument(0);
            user.setId(1L); // Симулируем установку ID
            return user;
        });

        Long result = userService.addUser(botUserDTO);

        assertEquals(1L, result);
        verify(userRepository, times(1)).save(any(BotUser.class));
    }

    @Test
    void testChangeCondition() {
        BotUser user = new BotUser();
        user.setId(1L);
        user.setChatId("12345");
        user.setCondition("default");

        when(userRepository.findByChatId("12345")).thenReturn(user);

        userService.changeCondition(12345L, "active");

        assertEquals("active", user.getCondition());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCondition() {
        BotUser user = new BotUser();
        user.setChatId("12345");
        user.setCondition("active");

        when(userRepository.findByChatId("12345")).thenReturn(user);

        String condition = userService.condition(12345L);

        assertEquals("active", condition);
    }
}
