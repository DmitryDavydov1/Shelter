package com.example.bot._for_shelter.repository;

import com.example.bot._for_shelter.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Pet}.
 * Содержит методы для доступа и работы с данными о питомцах, включая поиск питомцев,
 * которые находятся в различных состояниях (например, имеют владельца или нет).
 */
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    /**
     * Находит все питомцы по состоянию наличия владельца.
     *
     * @param b Статус наличия владельца: true — питомец имеет владельца, false — питомец не имеет владельца.
     * @return Список питомцев, у которых соответствующий статус {@code haveOwner}.
     */
    List<Pet> findAllByHaveOwner(boolean b);
}
