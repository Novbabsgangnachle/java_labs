package ru.dto;

import lombok.Data;

import java.util.List;

@Data
public class OwnerDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<PetDto> pets;
    private PostDto post;
}