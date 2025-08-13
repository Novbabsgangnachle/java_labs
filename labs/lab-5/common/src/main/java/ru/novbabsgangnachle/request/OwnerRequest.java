package ru.novbabsgangnachle.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.novbabsgangnachle.dto.OwnerDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerRequest {
    private String type;
    private OwnerDto payload;

}
