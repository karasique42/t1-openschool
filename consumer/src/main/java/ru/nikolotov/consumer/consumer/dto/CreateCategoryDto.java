package ru.nikolotov.consumer.consumer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDto {

    @NotBlank(message = "Имя категории не должно быть пустым")
    private String name;
}
