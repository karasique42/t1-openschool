package ru.nikolotov.consumer.consumer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {

    private UUID id;
    private String name;
    private String description;
    private Long price;
    private CategoryDto category;
}
