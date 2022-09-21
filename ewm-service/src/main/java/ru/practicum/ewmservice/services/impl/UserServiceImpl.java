package ru.practicum.ewmservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.models.User;
import ru.practicum.ewmservice.repositories.UserRepository;
import ru.practicum.ewmservice.services.UserService;
import ru.practicum.ewmservice.traits.PageTrait;

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
    public User create(User user) {
        user = userRepository.save(user);

        log.info("CreateUser. Создан пользователь с id {}", user.getId());
        return user;
    }

    @Override
    public void delete(long id) throws ObjectNotFountException {
        if (checkExistsById(id)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", id),
                    "DeleteUser"
            );
        }

        userRepository.deleteById(id);

        log.info("DeleteUser. Удален пользователь с id {}", id);
    }

    @Override
    public User getById(long id) throws ObjectNotFountException {
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
        return !userRepository.existsById(id);
    }
}