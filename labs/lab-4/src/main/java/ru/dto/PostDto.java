package ru.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    BigDecimal price;
}