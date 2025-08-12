package ru.dto;

import lombok.Data;
import ru.domain.PetType;

@Data
public class PetDto {
    private long id;
    private String name;
    private PetType type;
    private int age;
}
