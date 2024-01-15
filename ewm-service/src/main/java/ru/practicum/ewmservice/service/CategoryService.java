package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.model.Category;

import java.util.Collection;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category);

    void delete(long id);

    Category getById(long id);

    Collection<Category> getAll(int from, int size);

    boolean checkExistsById(long id);
}
