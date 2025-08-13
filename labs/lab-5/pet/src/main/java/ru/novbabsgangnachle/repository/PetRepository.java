package ru.novbabsgangnachle.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.novbabsgangnachle.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}