package ru.practicum.ewmservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.model.User;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс репозитория пользователя
 */
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIdIn(Collection<Long> ids, Pageable page);
}
