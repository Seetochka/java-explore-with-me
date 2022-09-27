package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.repository.CategoryRepository;
import ru.practicum.ewmservice.service.CategoryService;
import ru.practicum.ewmservice.trait.PageTrait;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Сервис категорий
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService, PageTrait {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category create(Category category) {
        category = categoryRepository.save(category);

        log.info("CreateCategory. Создана категория с id {}", category.getId());
        return category;
    }

    @Override
    @Transactional
    public Category update(Category category) throws ObjectNotFountException {
        if (!checkExistsById(category.getId())) {
            throw new ObjectNotFountException(
                    String.format("Категория с id %d не существует", category.getId()),
                    "UpdateCategory"
            );
        }

        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(long id) throws ObjectNotFountException {
        if (!checkExistsById(id)) {
            throw new ObjectNotFountException(
                    String.format("Категория с id %d не существует", id),
                    "DeleteCategory"
            );
        }

        categoryRepository.deleteById(id);

        log.info("DeleteCategory. Удалена категория с id {}", id);
    }

    @Override
    public Category getById(long id) throws ObjectNotFountException {
        return categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Категории с id %d не существует", id),
                "GetCategoryById"
        ));
    }

    @Override
    public Collection<Category> getAll(int from, int size) {
        Pageable page = getPage(from, size, "id", Sort.Direction.ASC);

        return categoryRepository.findAll(page)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkExistsById(long id) {
        return categoryRepository.existsById(id);
    }
}
