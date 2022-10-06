package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.UserRepository;
import ru.practicum.ewmservice.service.UserService;
import ru.practicum.ewmservice.trait.PageTrait;

import java.util.Collection;

/**
 * Сервис пользователей
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, PageTrait {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User create(User user) {
        user = userRepository.save(user);
        log.info("CreateUser. Создан пользователь с id {}", user.getId());
        return user;
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!checkExistsById(id)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", id),
                    "DeleteUser"
            );
        }

        userRepository.deleteById(id);
        log.info("DeleteUser. Удален пользователь с id {}", id);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Пользователь с id %d не существует", id),
                "GetUserById"
        ));
    }

    @Override
    public Collection<User> getByIds(Collection<Long> ids, int from, int size) {
        Pageable page = getPage(from, size, "id", Sort.Direction.ASC);
        return userRepository.findAllByIdIn(ids, page);
    }

    @Override
    public boolean checkExistsById(long id) {
        return userRepository.existsById(id);
    }
}
