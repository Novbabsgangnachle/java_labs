package ru.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.domain.PetType;

@Data
public class UpdatePetRequest {
    @Min(value = 0)
    @Max(value = 200)
    private int age;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    private PetType type;
}
