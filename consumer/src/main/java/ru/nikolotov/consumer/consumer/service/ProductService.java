package ru.nikolotov.consumer.consumer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.nikolotov.consumer.consumer.dto.CreateProductDto;
import ru.nikolotov.consumer.consumer.dto.ProductDto;
import ru.nikolotov.consumer.consumer.exception.BadRequestException;
import ru.nikolotov.consumer.consumer.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final RestTemplate restTemplate;
    private final String supplierUrl;

    public ProductService(RestTemplate restTemplate, @Value("${supplier.url}") String supplierUrl) {
        this.restTemplate = restTemplate;
        this.supplierUrl = supplierUrl + "/products";
    }

    public ProductDto create(CreateProductDto dto) {
        try {
            return restTemplate.postForObject(supplierUrl, dto, ProductDto.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new BadRequestException(e.getMessage());
            }
            throw e;
        }
    }

    public List<ProductDto> getAll(String name, String description, Long maxPrice, Long minPrice,
                                   String categoryName, Integer pageNum, Integer pageSize) {
        ProductDto[] dto = restTemplate.getForObject(supplierUrl, ProductDto[].class,
                name, description, maxPrice, minPrice, categoryName, pageNum, pageSize);
        if (dto == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(dto);
    }

    public ProductDto get(UUID id) {
        try {
            return restTemplate.getForObject(supplierUrl + "/" + id, ProductDto.class);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException(e.getMessage());
            }
            throw e;
        }
    }

    public ProductDto update(UUID id, CreateProductDto dto) {
        try {
            HttpEntity<CreateProductDto> requestEntity = new HttpEntity<>(dto);
            return restTemplate.exchange(supplierUrl + "/" + id, HttpMethod.PUT, requestEntity, ProductDto.class)
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
