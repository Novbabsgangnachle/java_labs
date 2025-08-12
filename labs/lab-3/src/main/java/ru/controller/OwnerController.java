package ru.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dto.OwnerDto;
import ru.request.CreateOwnerRequest;
import ru.request.UpdateOwnerRequest;
import ru.response.ApiResponse;
import ru.service.OwnerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/owners")
public class OwnerController {
    private final OwnerService ownerService;

    private <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>("Success", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OwnerDto>> getOwnerById(@PathVariable long id) {
        OwnerDto ownerDto = ownerService.getOwnerDtoById(id);
        return ok(ownerDto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<OwnerDto>> getOwnerByEmail(@Email @PathVariable String email) {
        OwnerDto ownerDto = ownerService.getOwnerDtoByEmail(email);
        return ok(ownerDto);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OwnerDto>> createOwner(@Valid @RequestBody CreateOwnerRequest request) {
        OwnerDto ownerDto = ownerService.createOwner(request);
        return ok(ownerDto);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<ApiResponse<OwnerDto>> updateOwner(@PathVariable long ownerId,
                                                             @Valid @RequestBody UpdateOwnerRequest request) {
        OwnerDto ownerDto = ownerService.updateOwner(ownerId, request);
        return ok(ownerDto);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<ApiResponse<Void>> deleteOwner(@PathVariable long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ok(null);
    }
}