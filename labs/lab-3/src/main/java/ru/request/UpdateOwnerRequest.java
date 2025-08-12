package ru.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateOwnerRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;
}