package ru.nikolotov.supplier.supplier.mapper;

import org.mapstruct.Mapper;
import ru.nikolotov.supplier.supplier.controller.dto.CategoryDto;
import ru.nikolotov.supplier.supplier.controller.dto.CreateCategoryDto;
import ru.nikolotov.supplier.supplier.repository.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Category dtoToEntity(CreateCategoryDto dto);

    CategoryDto entityToDto(Category dto);

    List<CategoryDto> entitiesToDtos(List<Category> dto);
}
