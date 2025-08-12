package ru.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.domain.Owner;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByEmail(String email);
    boolean existsByEmail(String email);
}
