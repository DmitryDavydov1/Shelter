package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с пользователями бота.
 * Этот контроллер предоставляет API для добавления нового пользователя в систему.
 */
@RestController
@RequestMapping(path = "bot-user")
public class UserController {

    /**
     * Сервис для обработки логики, связанной с пользователями бота.
     * Используется для добавления нового пользователя.
     */
    @Autowired
    UserService userService;

    /**
     * Метод для добавления нового пользователя.
     * Принимает данные пользователя в формате JSON, преобразует их в объект BotUserDTO,
     * и передает в сервис для обработки. Возвращает объект BotUser, содержащий информацию
     * о добавленном пользователе.
     *
     * @param BotUserDTO объект с данными нового пользователя, переданными в теле запроса.
     * @return объект BotUser с информацией о добавленном пользователе.
     */
    @PostMapping
    public BotUser addUser(@RequestBody BotUserDTO BotUserDTO) {
        return userService.addUser(BotUserDTO);
    }
}
