package com.example.bot._for_shelter.repository;


import com.example.bot._for_shelter.model.PhotoTg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoTgRepository extends JpaRepository<PhotoTg, Long> {
}
