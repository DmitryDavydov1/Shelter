package com.example.bot._for_shelter.repository;


import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Integer> {

    boolean existsByBotUser(BotUser userId);

    boolean existsByBotUserId(Long userId);
}
