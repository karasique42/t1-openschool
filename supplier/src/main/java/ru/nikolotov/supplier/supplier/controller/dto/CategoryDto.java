package ru.nikolotov.supplier.supplier.controller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {

    private UUID id;
    private String name;
}
