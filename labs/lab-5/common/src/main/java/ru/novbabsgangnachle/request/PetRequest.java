package ru.novbabsgangnachle.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.novbabsgangnachle.dto.PetDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequest {
    private String type;
    private PetDto payload;
}
