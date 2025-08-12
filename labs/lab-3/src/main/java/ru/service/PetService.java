package ru.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.domain.Owner;
import ru.domain.Pet;
import ru.domain.PetType;
import ru.dto.PetDto;
import ru.exceptions.ResourceNotFoundException;
import ru.repository.OwnerRepository;
import ru.repository.PetRepository;
import ru.request.CreatePetRequest;
import ru.request.UpdatePetRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PetService {
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private final OwnerRepository ownerRepository;

    public PetDto getPetById(long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        return convertToDto(pet);
    }

    public PetDto createPet(CreatePetRequest request) {
        Pet pet = new Pet();
        pet.setName(request.getName());
        pet.setAge(request.getAge());
        pet.setType(request.getType());
        Pet saved = petRepository.save(pet);
        return convertToDto(saved);
    }

    public PetDto updatePet(UpdatePetRequest request, long id) {
        Pet updated = petRepository.findById(id).map(existingPet -> {
            existingPet.setName(request.getName());
            existingPet.setAge(request.getAge());
            existingPet.setType(request.getType());
            return petRepository.save(existingPet);
        }).orElseThrow(() -> new ResourceNotFoundException("Pet not found"));

        return convertToDto(updated);
    }

    public void deletePet(long id) {
        petRepository.findById(id).ifPresentOrElse(petRepository::delete, () -> {
            throw new ResourceNotFoundException("Pet not found");
        });
    }

    public List<PetDto> getPetsByType(PetType type) {
        List<Pet> pets = (type == null) ? petRepository.findAll() : petRepository.findByType(type);
        return convertToDto(pets);
    }

    public List<PetDto> getPetsByNameContaining(String name) {
        List<Pet> pets = (name == null || name.isEmpty())
                ? petRepository.findAll()
                : petRepository.findByNameContainingIgnoreCase(name);
        return convertToDto(pets);
    }

    public List<PetDto> getPetsByOwnerId(long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        List<Pet> pets = petRepository.findByOwner(owner);
        return convertToDto(pets);
    }

    public long countPetsByType(PetType type) {
        return (type == null) ? petRepository.count() : petRepository.countByType(type);
    }

    public List<PetDto> getPetsWithAgeBelow(int age) {
        if (age < 0)
            throw new IllegalArgumentException("Age must be >= 0");
        List<Pet> pets = petRepository.findByAgeLessThan(age);
        return convertToDto(pets);
    }

    public List<PetDto> getPetsWithAgeAbove(int age) {
        if (age < 0)
            throw new IllegalArgumentException("Age must be >= 0");
        List<Pet> pets = petRepository.findByAgeGreaterThan(age);
        return convertToDto(pets);
    }

    private PetDto convertToDto(Pet pet) {
        return modelMapper.map(pet, PetDto.class);
    }

    private List<PetDto> convertToDto(List<Pet> pets) {
        return pets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
