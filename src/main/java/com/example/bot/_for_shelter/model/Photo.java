package com.example.bot._for_shelter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] preview;
    @OneToOne
    private Report report;
}
