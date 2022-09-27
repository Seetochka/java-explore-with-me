package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.model.Category;

import java.util.Collection;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category) throws ObjectNotFountException;

    void delete(long id) throws ObjectNotFountException;

    Category getById(long id) throws ObjectNotFountException;

    Collection<Category> getAll(int from, int size);

    boolean checkExistsById(long id);
}
