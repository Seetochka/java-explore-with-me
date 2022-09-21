package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.clients.EventClient;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.exceptions.EventStateException;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.exceptions.UserHaveNoRightsException;
import ru.practicum.ewmservice.mappers.EventMapper;
import ru.practicum.ewmservice.mappers.ParticipationRequestMapper;
import ru.practicum.ewmservice.models.Event;
import ru.practicum.ewmservice.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер отвечающий за действия с событиями
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ParticipationRequestMapper participationRequestMapper;

    private final EventClient eventClient;

    @GetMapping(path = "/events")
    public Collection<EventShortDto> getByParams(@RequestParam String text,
                                                 @RequestParam Collection<Long> categories,
                                                 @RequestParam Boolean paid,
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime rangeStart,
                                                 @RequestParam
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                 LocalDateTime rangeEnd,
                                                 @RequestParam Boolean onlyAvailable,
                                                 @RequestParam EventSort sort,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 HttpServletRequest httpRequest) {

        Collection<Event> events = eventService.getByParamsForUser(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);

        eventClient.createStat(httpRequest);

        return events.stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/events/{id}")
    public EventFullDto getById(@PathVariable long id, HttpServletRequest httpRequest) throws ObjectNotFountException {
        Event event = eventService.getById(id);

        eventClient.createStat(httpRequest);

        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events")
    public Collection<EventShortDto> getByUserId(@PathVariable long userId,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size)
            throws ObjectNotFountException {
        return eventService.getByUserId(userId, from, size)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto update(@PathVariable long userId, @Valid @RequestBody UpdateEventRequestDto eventRequest)
            throws ObjectNotFountException, UserHaveNoRightsException {
        Event event = eventService.updateUser(userId, eventMapper.toEventForUpdate(eventRequest));

        return eventMapper.toEventFullDto(event);
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody NewEventDto eventDto)
            throws ObjectNotFountException {
        Event event = eventService.create(userId, eventMapper.toEventForCreate(eventDto));

        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId)
            throws UserHaveNoRightsException, ObjectNotFountException {
        return eventMapper.toEventFullDto(eventService.getByUserIdAndEventId(userId, eventId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancel(@PathVariable long userId, @PathVariable long eventId)
            throws ObjectNotFountException, EventStateException, UserHaveNoRightsException {
        Event event = eventService.cancelEvent(userId, eventId);

        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequestsByEventId(@PathVariable long userId,
                                                                    @PathVariable long eventId)
            throws ObjectNotFountException {
        return eventService.getRequestsByEventId(userId, eventId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(@PathVariable long userId,
                                                               @PathVariable long eventId,
                                                               @PathVariable long reqId) throws ObjectNotFountException {
        return participationRequestMapper.toParticipationRequestDto(
                eventService.confirmParticipationRequest(userId, eventId, reqId)
        );
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipationRequest(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @PathVariable long reqId) throws ObjectNotFountException {
        return participationRequestMapper.toParticipationRequestDto(
                eventService.rejectParticipationRequest(userId, eventId, reqId)
        );
    }
}
