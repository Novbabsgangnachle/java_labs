package ru.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.domain.Owner;
import ru.repository.OwnerRepository;
import ru.repository.PetRepository;

@RequiredArgsConstructor
@Component("petSecurity")
public class PetSecurity {
    private final PetRepository repo;
    private final OwnerRepository ownerRepo;

    public boolean isOwnerOrAdmin(Authentication auth, Long catId) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            return true;
        String email = auth.getName();
        return repo.findById(catId)
                .map(c -> c.getOwner().getEmail().equals(email))
                .orElse(false);
    }
    public boolean isOwner(Authentication auth, Long id) {
        String email = auth.getName();
        Owner owner = ownerRepo.findByEmail(email).orElse(null);
        return repo.findById(id)
                .map(c -> c.getOwner().getEmail().equals(email))
                .orElse(false);
    }
}
