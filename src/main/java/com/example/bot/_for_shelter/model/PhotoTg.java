package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PhotoTg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatId;

    private String fileId;

    @OneToOne
    private Report report;

    private boolean viewed;

}
