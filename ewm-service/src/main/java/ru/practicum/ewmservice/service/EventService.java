package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.exception.EventStateException;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;

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
                                          String rangeStart, String rangeEnd, int from, int size);

    Collection<Event> getByParamsForUser(String text, Collection<Long> categories, Boolean paid,
                                         String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                         EventSort sort, int from, int size);

    Event getById(long id) throws ObjectNotFountException;

    Collection<Event> getByUserId(long userId, int from, int size) throws ObjectNotFountException;

    Event getByUserIdAndEventId(long userId, long eventId) throws ObjectNotFountException, UserHaveNoRightsException;

    Collection<ParticipationRequest> getRequestsByEventId(long userId, long eventId) throws ObjectNotFountException;

    boolean checkExistsById(long id);
}
