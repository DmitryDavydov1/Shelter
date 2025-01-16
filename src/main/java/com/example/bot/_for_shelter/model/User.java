package com.example.bot._for_shelter.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class User {

    Integer chatId;
    String phoneNumber;
    String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор пользователя

    public User(Integer chatId, String phoneNumber, String name) {
        this.chatId = chatId;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public User() {}

}
