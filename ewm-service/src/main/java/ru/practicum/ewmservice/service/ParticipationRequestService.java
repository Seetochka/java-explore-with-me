package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.exception.EventParticipantLimitException;
import ru.practicum.ewmservice.exception.EventStateException;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.Collection;

public interface ParticipationRequestService {
    ParticipationRequest create(long userId, long eventId)
            throws ObjectNotFountException, UserHaveNoRightsException, EventStateException, EventParticipantLimitException;

    ParticipationRequest cancelParticipationRequest(long userId, long requestId) throws ObjectNotFountException;

    ParticipationRequest getById(long id) throws ObjectNotFountException;

    Collection<ParticipationRequest> getByUserId(long userId) throws ObjectNotFountException;

    int getCountParticipantByEventId(long eventId);

    boolean checkExistsById(long id);
}
