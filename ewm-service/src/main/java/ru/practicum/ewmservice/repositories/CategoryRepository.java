package ru.practicum.ewmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.models.Category;

/**
 * Интерфейс репозитория категорий
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
