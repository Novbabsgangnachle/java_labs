package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.domain.Owner;
import ru.domain.Pet;
import ru.domain.PetType;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByType(PetType type);
    List<Pet> findByNameContainingIgnoreCase(String name);
    List<Pet> findByOwner(Owner owner);
    long countByType(PetType type);
    List<Pet> findByAgeGreaterThan(int age);
    List<Pet> findByAgeLessThan(int age);

}
