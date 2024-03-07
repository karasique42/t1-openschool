package ru.nikolotov.consumer.consumer.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nikolotov.consumer.consumer.dto.CreateProductDto;
import ru.nikolotov.consumer.consumer.dto.ProductDto;
import ru.nikolotov.consumer.consumer.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto createCategory(@RequestBody @Valid CreateProductDto dto) {
        return productService.create(dto);
    }

    @GetMapping
    public List<ProductDto> getProducts(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) Long maxPrice,
                                        @RequestParam(required = false) Long minPrice,
                                        @RequestParam(required = false) String categoryName,
                                        @RequestParam(defaultValue = "0") Integer pageNum,
                                        @RequestParam(defaultValue = "20") Integer pageSize) {
        return productService.getAll(name, description, maxPrice, minPrice, categoryName,
                pageNum, pageSize);
    }

    @GetMapping(path = "/{id}")
    public ProductDto getCategories(@PathVariable @NotNull UUID id) {
        return productService.get(id);
    }

    @PutMapping(path = "/{id}")
    public ProductDto updateCategory(@PathVariable @NotNull UUID id,
                                     @RequestBody @Valid CreateProductDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable @NotNull UUID id) {
        productService.delete(id);
    }
}
