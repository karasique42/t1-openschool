package ru.nikolotov.supplier.supplier.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikolotov.supplier.supplier.controller.dto.CategoryDto;
import ru.nikolotov.supplier.supplier.controller.dto.CreateCategoryDto;
import ru.nikolotov.supplier.supplier.service.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CreateCategoryDto category) {
        return categoryService.create(category);
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.getAll();
    }

    @GetMapping(path = "/{id}")
    public CategoryDto getCategories(@PathVariable @NotNull UUID id) {
        return categoryService.get(id);
    }

    @PutMapping(path = "/{id}")
    public CategoryDto updateCategory(@PathVariable @NotNull UUID id, @Valid @RequestBody CreateCategoryDto dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable @NotNull UUID id) {
        categoryService.delete(id);
    }
}
