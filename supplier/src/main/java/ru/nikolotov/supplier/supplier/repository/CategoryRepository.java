package ru.nikolotov.supplier.supplier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikolotov.supplier.supplier.repository.entity.Category;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
