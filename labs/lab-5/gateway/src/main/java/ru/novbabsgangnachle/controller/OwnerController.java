package ru.novbabsgangnachle.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.novbabsgangnachle.dto.OwnerDto;
import ru.novbabsgangnachle.request.OwnerRequest;
import ru.novbabsgangnachle.response.OwnerResponse;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponse> getById(@PathVariable Long id) {
        OwnerDto dto = new OwnerDto();
        dto.setId(id);
        OwnerRequest req = new OwnerRequest("GET", dto);
        OwnerResponse resp = (OwnerResponse) rabbitTemplate
                .convertSendAndReceive("appExchange","owner", req);

        return ResponseEntity
                .status(resp.getStatus().equals("SUCCESS") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }

    @PostMapping
    public ResponseEntity<OwnerResponse> create(@Valid @RequestBody OwnerDto dto) {
        OwnerRequest req = new OwnerRequest("CREATE", dto);
        OwnerResponse resp = (OwnerResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "owner", req);
        return ResponseEntity
                .status(resp.getStatus().equals("SUCCESS") ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody OwnerDto dto
    ) {
        dto.setId(id);
        OwnerRequest req = new OwnerRequest("UPDATE", dto);
        OwnerResponse resp = (OwnerResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "owner", req);
        return ResponseEntity
                .status(resp.getStatus().equals("SUCCESS") ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OwnerResponse> delete(@PathVariable Long id) {
        OwnerDto dto = new OwnerDto();
        dto.setId(id);
        OwnerRequest req = new OwnerRequest("DELETE", dto);
        OwnerResponse resp = (OwnerResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "owner", req);
        return ResponseEntity
                .status(resp.getStatus().equals("SUCCESS") ? HttpStatus.NO_CONTENT : HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp);
    }
}
