package ru.practicum.ewmservice.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.ewmservice.models.Event;

import java.util.List;

/**
 * Интерфейс репозитория события
 */
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByInitiatorId(long id, Pageable page);
}
