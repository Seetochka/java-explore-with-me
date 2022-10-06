package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.Collection;

public interface ParticipationRequestService {
    ParticipationRequest create(long userId, long eventId);

    ParticipationRequest cancelParticipationRequest(long userId, long requestId);

    ParticipationRequest getById(long id);

    Collection<ParticipationRequest> getByUserId(long userId);

    int getCountParticipantByEventId(long eventId);

    boolean checkExistsById(long id);
}
