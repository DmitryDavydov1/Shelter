package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.model.PetDTO;
import com.example.bot._for_shelter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления питомцами в приюте.
 * Включает методы для добавления питомцев и обновления их статуса владения.
 */
@Service
public class PetService {

    @Autowired
    PetRepository petRepository;

    /**
     * Добавляет нового питомца в базу данных.
     *
     * @param petDTO DTO, содержащий информацию о питомце.
     * @return созданный {@link Pet} объект.
     */
    public Pet addPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setAge(petDTO.getAge());
        pet.setGender(petDTO.getGender());
        pet.setWeight(petDTO.getWeight());
        pet.setNickname(petDTO.getNickname());
        pet.setHaveOwner(false);
        return petRepository.save(pet);
    }

    /**
     * Обновляет статус питомца, установив, что он имеет владельца.
     *
     * @param petId ID питомца, чей статус владения будет изменен.
     */
    public void setHaveOwner(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            pet.setHaveOwner(true);
            petRepository.save(pet);
        }
    }
}
