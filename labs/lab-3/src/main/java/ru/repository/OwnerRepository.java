package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByEmail(String email);
    boolean existsByEmail(String email);
}