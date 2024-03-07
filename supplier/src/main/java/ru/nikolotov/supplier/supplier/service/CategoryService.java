package ru.nikolotov.supplier.supplier.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nikolotov.supplier.supplier.controller.dto.CategoryDto;
import ru.nikolotov.supplier.supplier.controller.dto.CreateCategoryDto;
import ru.nikolotov.supplier.supplier.mapper.CategoryMapper;
import ru.nikolotov.supplier.supplier.repository.CategoryRepository;
import ru.nikolotov.supplier.supplier.repository.entity.Category;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto create(CreateCategoryDto createCategoryDto) {
        var entity = categoryMapper.dtoToEntity(createCategoryDto);
        categoryRepository.save(entity);
        return categoryMapper.entityToDto(entity);
    }

    public List<CategoryDto> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.entitiesToDtos(categories);
    }

    public CategoryDto get(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::entityToDto)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " was not found"));
    }

    public CategoryDto update(UUID id, CreateCategoryDto dto) {
        return categoryRepository.findById(id)
                .map(entity -> categoryRepository.save(entity.setName(dto.getName())))
                .map(categoryMapper::entityToDto)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " was not found"));
    }

    public void delete(UUID id) {
        categoryRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " was not found"));
        categoryRepository.deleteById(id);
    }
}
