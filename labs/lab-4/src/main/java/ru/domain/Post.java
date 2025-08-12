package ru.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false)
    @Size(min = 2, max = 50)
    @NotBlank
    private String title;

    @Column(nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String description;

    @Min(value = 0, message = "Цена должна быть не меньше 0")
    @Max(value = 100000000, message = "Цена не должна быть больше 100.000.000")
    @Column(nullable = false)
    private BigDecimal price;
}
