package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.enums.ParticipationRequestStatus;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.List;

/**
 * Интерфейс репозитория
 */
public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByEventId(long eventId);

    List<ParticipationRequest> findAllByRequesterId(long userId);

    Integer countByEventIdAndStatus(long eventId, ParticipationRequestStatus approved);
}
