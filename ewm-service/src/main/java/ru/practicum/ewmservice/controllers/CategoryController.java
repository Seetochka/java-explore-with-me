package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.services.CategoryService;
import ru.practicum.ewmservice.dto.CategoryDto;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.mappers.CategoryMapper;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер отвечающий за действия с категориями
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/categories/{catId}")
    public CategoryDto getById(@PathVariable long catId) throws ObjectNotFountException {
        return categoryMapper.toCategoryDto((categoryService.getById(catId)));
    }

    @GetMapping("/categories")
    public Collection<CategoryDto> getAllByUserId(@RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        return categoryService.getAll(from, size)
                .stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }
}
