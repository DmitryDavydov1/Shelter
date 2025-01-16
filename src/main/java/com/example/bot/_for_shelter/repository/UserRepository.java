package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
