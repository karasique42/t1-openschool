package ru.nikolotov.consumer.consumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.nikolotov.consumer.consumer.dto.CategoryDto;
import ru.nikolotov.consumer.consumer.dto.CreateCategoryDto;
import ru.nikolotov.consumer.consumer.exception.BadRequestException;
import ru.nikolotov.consumer.consumer.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final RestTemplate restTemplate;
    private final String supplierUrl;

    public CategoryService(RestTemplate restTemplate, @Value("${supplier.url}") String supplierUrl) {
        this.restTemplate = restTemplate;
        this.supplierUrl = supplierUrl + "/categories";
    }

    public CategoryDto create(CreateCategoryDto categoryDto) {
        try {
            return restTemplate.postForObject(supplierUrl, categoryDto, CategoryDto.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new BadRequestException(e.getMessage());
            }
            throw e;
        }
    }

    public List<CategoryDto> getAll() {
        CategoryDto[] dto = restTemplate.getForObject(supplierUrl, CategoryDto[].class);
        if (dto == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(dto);
    }

    public CategoryDto get(UUID id) {
        try {
            return restTemplate.getForObject(supplierUrl + "/" + id, CategoryDto.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException(e.getMessage());
            }
            throw e;
        }
    }

    public CategoryDto update(UUID id, CreateCategoryDto dto) {
        try {
            HttpEntity<CreateCategoryDto> requestEntity = new HttpEntity<>(dto);
            return restTemplate.exchange(supplierUrl + "/" + id, HttpMethod.PUT, requestEntity, CategoryDto.class)
                    .getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException(e.getMessage());
            }
            throw e;
        }
    }

    public void delete(UUID id) {
        try {
            restTemplate.delete(supplierUrl + "/" + id);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException(e.getMessage());
            }
            throw e;
        }
    }
}

