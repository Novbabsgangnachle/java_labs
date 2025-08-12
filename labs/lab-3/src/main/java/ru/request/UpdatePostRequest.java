package ru.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdatePostRequest {
    @NotBlank
    @Size(min = 2, max = 50)
    private String title;
    @NotBlank
    @Size(max = 100)
    private String description;
    @Min(0)
    @Max(100000000)
    private BigDecimal price;
}
