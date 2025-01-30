package com.example.bot._for_shelter.repository;


import com.example.bot._for_shelter.model.Adoption;
import com.example.bot._for_shelter.model.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, Integer> {


    boolean existsByBotUserId(Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Adoption a SET a.currentDay = a.currentDay")
    void addOneDay();
}
