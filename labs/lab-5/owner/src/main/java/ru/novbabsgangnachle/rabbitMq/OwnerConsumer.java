package ru.novbabsgangnachle.rabbitMq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.novbabsgangnachle.dto.OwnerDto;
import ru.novbabsgangnachle.domain.Owner;
import ru.novbabsgangnachle.repository.OwnerRepository;
import ru.novbabsgangnachle.request.OwnerRequest;
import ru.novbabsgangnachle.response.OwnerResponse;

@Service
@AllArgsConstructor
public class OwnerConsumer {
    private final OwnerRepository repo;

    @RabbitListener(queues = RabbitConfigOwner.OWNER_QUEUE)
    public OwnerResponse listen(OwnerRequest req) {
        OwnerResponse resp = new OwnerResponse();
        try {
            switch (req.getType()) {
                case "GET" -> {
                    Owner o = repo.findById(req.getPayload().getId()).orElseThrow();
                    resp.setPayload(mapToDto(o));
                }
                case "CREATE" -> {
                    OwnerDto p = req.getPayload();
                    Owner saved = repo.save(new Owner(null, p.getFirstName(), p.getLastName(), p.getEmail()));
                    resp.setPayload(mapToDto(saved));
                }
                case "UPDATE" -> {
                    OwnerDto p = req.getPayload();
                    Owner e = repo.findById(p.getId()).orElseThrow();
                    e.setFirstName(p.getFirstName());
                    e.setLastName(p.getLastName());
                    e.setEmail(p.getEmail());
                    resp.setPayload(mapToDto(repo.save(e)));
                }
                case "DELETE" -> {
                    repo.deleteById(req.getPayload().getId());
                }
                default -> throw new IllegalArgumentException("Unknown type: " + req.getType());
            }
            resp.setStatus("SUCCESS");
        } catch (Exception e) {
            resp.setStatus("ERROR");
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    private OwnerDto mapToDto(Owner o) {
        return new OwnerDto(o.getId(), o.getFirstName(), o.getLastName(), o.getEmail());
    }
}
