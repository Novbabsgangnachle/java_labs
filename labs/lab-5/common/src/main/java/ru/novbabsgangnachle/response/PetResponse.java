package ru.novbabsgangnachle.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.novbabsgangnachle.dto.PetDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {
    private String status;
    private PetDto payload;
    private String message;
}

