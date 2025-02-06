package com.example.bot._for_shelter.service;

import com.example.bot._for_shelter.model.Pet;
import com.example.bot._for_shelter.DTO.PetDTO;
import com.example.bot._for_shelter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return id нового объекта Pet.
     */
    @CacheEvict(value = "petsWithoutOwner", allEntries = true)
    public Long addPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setAge(petDTO.getAge());
        pet.setGender(petDTO.getGender());
        pet.setWeight(petDTO.getWeight());
        pet.setNickname(petDTO.getNickname());
        pet.setHaveOwner(false);
        petRepository.save(pet);
        return pet.getId();
    }


    /**
     * Обновляет статус питомца, установив, что он имеет владельца.
     *
     * @param petId ID питомца, чей статус владения будет изменен.
     */

    @Caching(evict = {
            @CacheEvict(value = "petsWithoutOwner", allEntries = true)
    })
    public void setHaveOwner(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet != null) {
            pet.setHaveOwner(true);
            petRepository.save(pet);
        }
    }

    /**
     * Ищет всех питомцев у которых есть или нет хозяина.
     *
     * @param b boolean тип для поля haveOwner
     * @return cписок объектов Pet у которых есть или нет хозяина.
     */
    @Cacheable(value = "petsWithoutOwner")
    public List<Pet> findAllByHaveOwner(boolean b) {
        return petRepository.findAllByHaveOwner(b);
    }

}
