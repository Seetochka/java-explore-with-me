package ru.practicum.ewmservice.services;

import ru.practicum.ewmservice.exceptions.EventParticipantLimitException;
import ru.practicum.ewmservice.exceptions.EventStateException;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.exceptions.UserHaveNoRightsException;
import ru.practicum.ewmservice.models.ParticipationRequest;

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
