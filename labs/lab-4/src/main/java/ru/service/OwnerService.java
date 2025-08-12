package ru.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.domain.Owner;
import ru.domain.Role;
import ru.dto.OwnerDto;
import ru.exceptions.AlreadyExistsException;
import ru.exceptions.ResourceNotFoundException;
import ru.repository.OwnerRepository;
import ru.request.CreateOwnerRequest;
import ru.request.UpdateOwnerRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {


    private final OwnerRepository ownerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public OwnerDto getOwnerDtoById(long id) {
        return convertOwnerToDto(ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found")));
    }

    public OwnerDto getOwnerDtoByEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException("Email cannot be null");
        return convertOwnerToDto(ownerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found")));
    }

    public OwnerDto createOwner(CreateOwnerRequest request) {
        return Optional.of(request)
                .filter(owner -> !ownerRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    Owner owner = new Owner();
                    owner.setEmail(request.getEmail());
                    owner.setFirstName(req.getFirstName());
                    owner.setLastName(req.getLastName());
                    String pass = passwordEncoder.encode(req.getPassword());
                    owner.getRoles().add(Role.ROLE_USER);
                    owner.setPassword(pass);
                    return convertOwnerToDto(ownerRepository.save(owner));
                }).orElseThrow( () -> new AlreadyExistsException("Could not create owner"));
    }

    public OwnerDto updateOwner(long id, UpdateOwnerRequest request) {
        return ownerRepository.findById(id).map(exsitingOwner ->{
            exsitingOwner.setFirstName(request.getFirstName());
            exsitingOwner.setLastName(request.getLastName());
            return convertOwnerToDto(ownerRepository.save(exsitingOwner));
        }).orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
    }
    public void deleteOwner(long id){
        ownerRepository.findById(id).ifPresentOrElse(ownerRepository::delete, () -> {
            throw new ResourceNotFoundException("Owner with id " + id + " not found");
        });
    }
    public OwnerDto convertOwnerToDto(Owner owner) {
        return modelMapper.map(owner, OwnerDto.class);
    }
}
