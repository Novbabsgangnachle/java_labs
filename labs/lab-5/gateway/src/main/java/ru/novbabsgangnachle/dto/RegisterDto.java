package ru.novbabsgangnachle.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private List<String> roles;
}