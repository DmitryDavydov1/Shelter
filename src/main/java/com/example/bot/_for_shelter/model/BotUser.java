package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity // Изменил дефис на подчеркивание
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String chatId;


    private String name;


    private String phoneNumber;

    private String condition;

    public BotUser() {
    }
}
