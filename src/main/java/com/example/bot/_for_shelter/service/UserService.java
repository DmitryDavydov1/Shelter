package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.BotUserDTO;
import com.example.bot._for_shelter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public BotUser addUser(BotUserDTO BotUserDTO) {
        BotUser botUser = new BotUser();
        String chatId = BotUserDTO.getChatId();
        botUser.setName(BotUserDTO.getName());
        botUser.setChatId(chatId);
        botUser.setPhoneNumber(BotUserDTO.getPhoneNumber());
        botUser.setCondition("default");
        if (!userRepository.existsByChatId(chatId)) {
            userRepository.save(botUser);
        }
        return botUser;
    }


    public void changeCondition(Long chatId, String newCondition) {
        BotUser user = userRepository.findByChatId(String.valueOf(chatId));
        user.setCondition(newCondition);
        userRepository.save(user);
    }

    public String condition(Long chatId) {
        return userRepository.findByChatId(String.valueOf(chatId)).getCondition();
    }
}
