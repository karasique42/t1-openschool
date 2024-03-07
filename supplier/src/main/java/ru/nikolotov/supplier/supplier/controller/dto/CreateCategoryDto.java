package ru.nikolotov.supplier.supplier.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {

    @NotBlank(message = "Имя категории не должно быть пустым")
    private String name;
}
