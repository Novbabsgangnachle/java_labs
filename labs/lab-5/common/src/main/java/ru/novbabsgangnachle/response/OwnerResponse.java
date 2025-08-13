package ru.novbabsgangnachle.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.novbabsgangnachle.dto.OwnerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponse {
    private String status;
    private OwnerDto payload;
    private String message;
}