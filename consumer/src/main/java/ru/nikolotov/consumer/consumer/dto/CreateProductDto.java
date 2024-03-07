package ru.nikolotov.consumer.consumer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductDto {


    @NotBlank(message = "Имя продукта не должно быть пустым")
    private String name;
    private String description;

    @Positive(message = "Стоимость не должна быть меньше или равна нулю")
    private Long price;

    @NotNull(message = "ID категории не может быть пустым")
    private UUID categoryId;
}
