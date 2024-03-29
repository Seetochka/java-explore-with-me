package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.Category;

/**
 * Интерфейс репозитория категорий
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
