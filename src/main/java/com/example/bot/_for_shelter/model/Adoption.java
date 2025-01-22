package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Adoption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Pet pet;
    @OneToOne
    private BotUser botUser;
    private Integer currentDay;
    private Integer lastDay;
}
