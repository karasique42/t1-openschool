package ru.nikolotov.supplier.supplier.service;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.nikolotov.supplier.supplier.controller.dto.CreateProductDto;
import ru.nikolotov.supplier.supplier.controller.dto.ProductDto;
import ru.nikolotov.supplier.supplier.mapper.ProductMapper;
import ru.nikolotov.supplier.supplier.repository.CategoryRepository;
import ru.nikolotov.supplier.supplier.repository.ProductRepository;
import ru.nikolotov.supplier.supplier.repository.entity.Category_;
import ru.nikolotov.supplier.supplier.repository.entity.Product;
import ru.nikolotov.supplier.supplier.repository.entity.Product_;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductDto create(CreateProductDto dto) {
        var product = productMapper.dtoToEntity(dto);
        var category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new NoSuchElementException("Category with id " + dto.getCategoryId() + " was not found"));
        product.setCategory(category);
        productRepository.save(product);
        return productMapper.entityToDto(product);
    }

    public List<ProductDto> getAll(String name, String description, Long maxPrice, Long minPrice,
                                   String categoryName, Integer pageNum, Integer pageSize) {
        List<Product> products = productRepository.findAll(createProdcutSpecification(
                name, description, maxPrice, minPrice, categoryName), PageRequest.of(pageNum, pageSize))
                .getContent().stream().toList();
        return productMapper.entitiesToDtos(products);
    }

    public ProductDto get(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::entityToDto)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " was not found"));
    }

    public ProductDto update(UUID id, CreateProductDto dto) {
        var productEntity = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " was not found"));
        productMapper.updateEntity(productEntity, dto);
        var category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with id " + dto.getCategoryId() + " was not found"));
        productEntity.setCategory(category);
        productRepository.save(productEntity);
        return productMapper.entityToDto(productEntity);
    }

    public void delete(UUID id) {
        productRepository.findById(id)
                        .orElseThrow(() -> new NoSuchElementException("Product with id " + id + " was not found"));
        productRepository.deleteById(id);
    }

    private Specification<Product> createProdcutSpecification(String name, String description, Long maxPrice,
                                                              Long minPrice, String categoryName) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isBlank()) {
                predicates.add(builder.like(root.get(Product_.NAME), "%" + name + "%"));
            }
            if (description != null && !description.isBlank()) {
                predicates.add(builder.like(root.get(Product_.DESCRIPTION), "%" + description + "%"));
            }
            if (maxPrice != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Product_.PRICE), maxPrice));
            }
            if (minPrice != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Product_.PRICE), minPrice));
            }
            if (categoryName != null && !categoryName.isBlank()) {
                predicates.add(builder.like(root.get(Product_.CATEGORY).get(Category_.NAME),
                        "%" + categoryName + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
