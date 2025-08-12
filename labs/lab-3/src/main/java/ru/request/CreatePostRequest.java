package ru.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePostRequest {
    private final long ownerId;
    private final long petId;
    @NotBlank
    @Size(min = 2, max = 50)
    private String title;
    @NotBlank
    @Size(min = 2, max = 100)
    private String description;
    @Min(value = 0)
    @Max(value = 100000000)
    private BigDecimal price;
}
