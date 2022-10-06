package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.model.User;

import java.util.Collection;

public interface UserService {
    User create(User user);

    void delete(long id);

    User getById(long id);

    Collection<User> getByIds(Collection<Long> ids, int from, int size);

    boolean checkExistsById(long id);
}
