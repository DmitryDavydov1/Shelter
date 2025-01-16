package com.example.bot._for_shelter.controller;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "bot-user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public BotUser addUser(@RequestBody BotUserDTO BotUserDTO) {
        return userService.addUser(BotUserDTO);
    }
}
