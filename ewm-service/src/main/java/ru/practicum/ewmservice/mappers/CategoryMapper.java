package ru.practicum.ewmservice.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.models.Category;

/**
 * Маппер для категории
 */
@Component
public class CategoryMapper {
    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
