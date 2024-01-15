package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;

import java.util.Collection;
import java.util.Optional;

public interface EventService {

    Event create(long userId, Event event);

    Event updateAdmin(long eventId, Event event);

    Event updateUser(long userId, Event event);

    Event publish(long eventId);

    Event reject(long eventId);

    Event cancelEvent(long userId, long eventId);

    ParticipationRequest confirmParticipationRequest(long userId, long eventId, long reqId);

    ParticipationRequest rejectParticipationRequest(long userId, long eventId, long reqId);

    Collection<Event> getByParamsForAdmin(Collection<Long> users, Collection<EventState> states, Collection<Long> categories,
                                          String rangeStart, String rangeEnd, int from, int size);

    Collection<Event> getByParamsForUser(String text, Collection<Long> categories, Boolean paid,
                                         String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                         EventSort sort, int from, int size);

    Event getByIdOrThrow(long id);

    Optional<Event> getById(long id);

    Collection<Event> getByUserId(long userId, int from, int size);

    Event getByUserIdAndEventId(long userId, long eventId);

    Collection<ParticipationRequest> getRequestsByEventId(long userId, long eventId);

    boolean checkExistsById(long id);
}
