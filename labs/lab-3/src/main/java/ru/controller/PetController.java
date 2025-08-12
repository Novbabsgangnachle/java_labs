package ru.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.domain.PetType;
import ru.dto.PetDto;
import ru.request.CreatePetRequest;
import ru.request.UpdatePetRequest;
import ru.response.ApiResponse;
import ru.service.PetService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/pets")
public class PetController {
    private final PetService petService;

    private <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>("Success", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDto>> getPetById(@PathVariable long id) {
        return ok(petService.getPetById(id));
    }

    @GetMapping("/type")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByTypeName(@RequestParam String typeName) {
        PetType type = PetType.valueOf(typeName.toUpperCase());
        return ok(petService.getPetsByType(type));
    }

    @GetMapping("/type/count")
    public ResponseEntity<ApiResponse<Long>> getPetsByTypeNameCount(@RequestParam String typeName) {
        PetType type = PetType.valueOf(typeName.toUpperCase());
        return ok(petService.countPetsByType(type));
    }

    @GetMapping("/name/contains")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByNameContaining(@RequestParam String name) {
        return ok(petService.getPetsByNameContaining(name));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByOwnerId(@PathVariable long ownerId) {
        return ok(petService.getPetsByOwnerId(ownerId));
    }

    @GetMapping("/age/below")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByAgeBelow(@RequestParam int age) {
        return ok(petService.getPetsWithAgeBelow(age));
    }

    @GetMapping("/age/above")
    public ResponseEntity<ApiResponse<List<PetDto>>> getPetsByAgeAbove(@RequestParam int age) {
        return ok(petService.getPetsWithAgeAbove(age));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PetDto>> createPet(@Valid @RequestBody CreatePetRequest request) {
        return ok(petService.createPet(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDto>> updatePet(@PathVariable long id,
                                                         @Valid @RequestBody UpdatePetRequest request) {
        return ok(petService.updatePet(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePet(@PathVariable long id) {
        petService.deletePet(id);
        return ok(null);
    }
}