package com.example.bot._for_shelter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptionDTO {
    private int bot_user_id;

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public void setBot_user_id(int bot_user_id) {
        this.bot_user_id = bot_user_id;
    }

    private int pet_id;
}
