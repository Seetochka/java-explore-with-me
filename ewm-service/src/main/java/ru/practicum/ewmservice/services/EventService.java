package ru.practicum.ewmservice.services;

import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.exceptions.EventStateException;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.exceptions.UserHaveNoRightsException;
import ru.practicum.ewmservice.models.Event;
import ru.practicum.ewmservice.models.ParticipationRequest;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {

    Event create(long userId, Event event) throws ObjectNotFountException;

    Event updateAdmin(long eventId, Event event) throws ObjectNotFountException;

    Event updateUser(long userId, Event event) throws ObjectNotFountException, UserHaveNoRightsException;

    Event publish(long eventId) throws ObjectNotFountException;

    Event reject(long eventId) throws ObjectNotFountException;

    Event cancelEvent(long userId, long eventId) throws ObjectNotFountException, UserHaveNoRightsException, EventStateException;

    ParticipationRequest confirmParticipationRequest(long userId, long eventId, long reqId) throws ObjectNotFountException;

    ParticipationRequest rejectParticipationRequest(long userId, long eventId, long reqId) throws ObjectNotFountException;

    Collection<Event> getByParamsForAdmin(Collection<Long> users, Collection<EventState> states, Collection<Long> categories,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    Collection<Event> getByParamsForUser(String text, Collection<Long> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                         EventSort sort, int from, int size);

    Event getById(long id) throws ObjectNotFountException;

    Collection<Event> getByUserId(long userId, int from, int size) throws ObjectNotFountException;

    Event getByUserIdAndEventId(long userId, long eventId) throws ObjectNotFountException, UserHaveNoRightsException;

    Collection<ParticipationRequest> getRequestsByEventId(long userId, long eventId) throws ObjectNotFountException;

    boolean checkExistsById(long id);
}
