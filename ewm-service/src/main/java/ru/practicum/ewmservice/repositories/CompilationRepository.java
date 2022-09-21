package ru.practicum.ewmservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.models.Compilation;

import java.util.List;

/**
 * Интерфейс репозитория подборок
 */
public interface CompilationRepository  extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByPinned(boolean pinned, Pageable page);
}
