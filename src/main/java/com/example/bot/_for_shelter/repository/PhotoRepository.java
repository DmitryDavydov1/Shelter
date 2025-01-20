package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.BotUser;
import com.example.bot._for_shelter.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long>  {
}
