package ru.nikolotov.supplier.supplier.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nikolotov.supplier.supplier.controller.dto.CreateProductDto;
import ru.nikolotov.supplier.supplier.controller.dto.ProductDto;
import ru.nikolotov.supplier.supplier.repository.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    Product dtoToEntity(CreateProductDto dto);

    ProductDto entityToDto(Product entity);

    Product updateEntity(@MappingTarget Product target, CreateProductDto dto);

    List<ProductDto> entitiesToDtos(List<Product> entity);
}
