package ru.novbabsgangnachle.rabbitMq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.novbabsgangnachle.dto.PetDto;
import ru.novbabsgangnachle.domain.Pet;
import ru.novbabsgangnachle.repository.PetRepository;
import ru.novbabsgangnachle.request.PetRequest;
import ru.novbabsgangnachle.response.PetResponse;

@Service
@AllArgsConstructor
public class PetConsumer {
    private final PetRepository repo;

    @RabbitListener(queues = RabbitConfigPet.PET_QUEUE)
    public PetResponse listen(PetRequest req) {
        PetResponse resp = new PetResponse();
        try {
            switch (req.getType()) {
                case "GET" -> {
                    Pet p = repo.findById(req.getPayload().getId()).orElseThrow();
                    resp.setPayload(mapToDto(p));
                }
                case "CREATE" -> {
                    PetDto dto = req.getPayload();
                    Pet saved = repo.save(new Pet(null, dto.getName(), dto.getAge(), dto.getType()));
                    resp.setPayload(mapToDto(saved));
                }
                case "UPDATE" -> {
                    PetDto dto = req.getPayload();
                    Pet existing = repo.findById(dto.getId()).orElseThrow();
                    existing.setName(dto.getName());
                    existing.setAge(dto.getAge());
                    existing.setType(dto.getType());
                    resp.setPayload(mapToDto(repo.save(existing)));
                }
                case "DELETE" -> repo.deleteById(req.getPayload().getId());
                default -> throw new IllegalArgumentException("Unknown type: " + req.getType());
            }
            resp.setStatus("SUCCESS");
        } catch (Exception e) {
            resp.setStatus("ERROR");
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    private PetDto mapToDto(Pet p) {
        return new PetDto(p.getId(), p.getName(), p.getAge(), p.getType());
    }
}
