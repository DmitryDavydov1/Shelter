package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public BotUser addUser(BotUserDTO BotUserDTO) {
        BotUser botUser = new BotUser();
        botUser.setName(BotUserDTO.getName());
        botUser.setChatId(BotUserDTO.getChatId());
        botUser.setPhoneNumber(BotUserDTO.getPhoneNumber());
        userRepository.save(botUser);
        return botUser;
    }
}
