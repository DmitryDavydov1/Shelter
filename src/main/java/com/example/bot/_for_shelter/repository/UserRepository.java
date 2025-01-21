package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {

    boolean existsByChatId(String chatId);

    BotUser findByChatId(String chatId);
}
