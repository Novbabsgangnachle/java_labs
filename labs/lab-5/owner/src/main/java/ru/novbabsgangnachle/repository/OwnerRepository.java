package ru.novbabsgangnachle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.novbabsgangnachle.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
