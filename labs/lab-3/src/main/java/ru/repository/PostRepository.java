package ru.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.domain.Owner;
import ru.domain.Pet;
import ru.domain.Post;

import java.math.BigDecimal;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByOwner(Owner owner);
    List<Post> findByPriceLessThan(BigDecimal price);
    List<Post> findByPriceGreaterThan(BigDecimal price);
    List<Post> findByPriceBetween(BigDecimal min, BigDecimal max);
    List<Post> findByOwnerAndPet(Owner owner, Pet pet);
    List<Post> findByTitleContaining(String title);
}
