package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.mappers.CategoryMapper;
import ru.practicum.ewmservice.models.Category;
import ru.practicum.ewmservice.services.CategoryService;

import javax.validation.Valid;

/**
 * Контроллер админки отвечающий за действия с категориями
 */
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.create(categoryMapper.toCategory(categoryDto));

        return categoryMapper.toCategoryDto(category);
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) throws ObjectNotFountException {
        Category category = categoryService.update(categoryMapper.toCategory(categoryDto));

        return categoryMapper.toCategoryDto(category);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable long catId) throws ObjectNotFountException {
        categoryService.delete(catId);
    }
}
