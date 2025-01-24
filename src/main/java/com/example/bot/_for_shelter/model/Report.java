package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String text;
    String chatId;
    private LocalTime time;
    boolean viewed;
    boolean havePhoto;
}
