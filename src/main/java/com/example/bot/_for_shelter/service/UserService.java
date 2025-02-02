package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.DTO.BotUserDTO;
import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с пользователями бота.
 * Позволяет добавлять новых пользователей, изменять их состояние и получать текущее состояние пользователя.
 */
@Service
public class UserService {

    /** Репозиторий для работы с данными пользователей */
    @Autowired
    private UserRepository userRepository;

    /**
     * Добавляет нового пользователя в базу данных.
     * Если пользователь с таким chatId уже существует, новый пользователь не добавляется.
     *
     * @param BotUserDTO ДТО объекта пользователя, содержащего данные нового пользователя
     * @return id нового BotUser
     */
    public Long addUser(BotUserDTO BotUserDTO) {
        BotUser botUser = new BotUser();
        String chatId = BotUserDTO.getChatId();
        botUser.setName(BotUserDTO.getName());
        botUser.setChatId(chatId);
        botUser.setPhoneNumber(BotUserDTO.getPhoneNumber());
        botUser.setCondition("default");
        if (!userRepository.existsByChatId(chatId)) {
            userRepository.save(botUser);
        }
        return botUser.getId();
    }

    /**
     * Изменяет состояние пользователя на новое.
     *
     * @param chatId Идентификатор чата пользователя
     * @param newCondition Новое состояние пользователя
     */
    public void changeCondition(Long chatId, String newCondition) {
        BotUser user = userRepository.findByChatId(String.valueOf(chatId));
        user.setCondition(newCondition);
        userRepository.save(user);
    }

    /**
     * Получает текущее состояние пользователя.
     *
     * @param chatId Идентификатор чата пользователя
     * @return Текущее состояние пользователя
     */
    public String condition(Long chatId) {
        return userRepository.findByChatId(String.valueOf(chatId)).getCondition();
    }
}
