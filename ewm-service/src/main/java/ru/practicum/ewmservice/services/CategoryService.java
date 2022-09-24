package ru.practicum.ewmservice.services;

import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.models.Category;

import java.util.Collection;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category) throws ObjectNotFountException;

    void delete(long id) throws ObjectNotFountException;

    Category getById(long id) throws ObjectNotFountException;

    Collection<Category> getAll(int from, int size);

    boolean checkExistsById(long id);
}
