package ru.practicum.ewmservice.services;

import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.models.User;

import java.util.Collection;

public interface UserService {
    User create(User user);

    void delete(long id) throws ObjectNotFountException;

    User getById(long id) throws ObjectNotFountException;

    Collection<User> getByIds(Collection<Long> ids, int from, int size);

    boolean checkExistsById(long id);
}
