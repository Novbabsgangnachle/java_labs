package ru.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.domain.PetType;

@Data
public class CreatePetRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    String name;
    @Min(value = 0)
    @Max(value = 200)
    int age;
    PetType type;
}