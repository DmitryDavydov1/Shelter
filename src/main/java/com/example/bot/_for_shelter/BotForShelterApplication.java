package com.example.bot._for_shelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableCaching
public class BotForShelterApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotForShelterApplication.class, args);
    }

}
