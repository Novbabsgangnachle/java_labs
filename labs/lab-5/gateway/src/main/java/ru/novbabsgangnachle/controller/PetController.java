package ru.novbabsgangnachle.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.novbabsgangnachle.dto.PetDto;
import ru.novbabsgangnachle.request.PetRequest;
import ru.novbabsgangnachle.response.PetResponse;

@RestController
@RequestMapping("/api/pets")
@AllArgsConstructor
public class PetController {

    private final RabbitTemplate rabbitTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> getById(@PathVariable Long id) {
        PetDto dto = new PetDto();
        dto.setId(id);
        PetRequest req = new PetRequest("GET", dto);

        PetResponse resp = (PetResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "pet", req);

        HttpStatus status = "SUCCESS".equals(resp.getStatus())
                ? HttpStatus.OK
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(resp);
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@Valid @RequestBody PetDto dto) {
        PetRequest req = new PetRequest("CREATE", dto);

        PetResponse resp = (PetResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "pet", req);

        HttpStatus status = "SUCCESS".equals(resp.getStatus())
                ? HttpStatus.CREATED
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PetDto dto
    ) {
        dto.setId(id);
        PetRequest req = new PetRequest("UPDATE", dto);

        PetResponse resp = (PetResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "pet", req);

        HttpStatus status = "SUCCESS".equals(resp.getStatus())
                ? HttpStatus.OK
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetResponse> delete(@PathVariable Long id) {
        PetDto dto = new PetDto();
        dto.setId(id);
        PetRequest req = new PetRequest("DELETE", dto);

        PetResponse resp = (PetResponse) rabbitTemplate
                .convertSendAndReceive("appExchange", "pet", req);

        HttpStatus status = "SUCCESS".equals(resp.getStatus())
                ? HttpStatus.NO_CONTENT
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(resp);
    }
}
