package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.client.EventClient;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewEventDto;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.dto.UpdateEventRequestDto;
import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.mapper.ParticipationRequestMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
                                                 @RequestParam String rangeStart,
                                                 @RequestParam String rangeEnd,
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
    public EventFullDto getById(@PathVariable long id, HttpServletRequest httpRequest) {
        Event event = eventService.getByIdOrThrow(id);
        eventClient.createStat(httpRequest);
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events")
    public Collection<EventShortDto> getByUserId(@PathVariable long userId,
                                                 @RequestParam(defaultValue = "0") int from,
                                                 @RequestParam(defaultValue = "10") int size) {
        return eventService.getByUserId(userId, from, size)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events")
    public EventFullDto update(@PathVariable long userId, @Valid @RequestBody UpdateEventRequestDto eventRequest) {
        Event event = eventService.updateUser(userId, eventMapper.toEventForUpdate(eventRequest));
        return eventMapper.toEventFullDto(event);
    }

    @PostMapping("/users/{userId}/events")
    public EventFullDto create(@PathVariable long userId, @Valid @RequestBody NewEventDto eventDto) {
        Event event = eventService.create(userId, eventMapper.toEventForCreate(eventDto));
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getByUserIdAndEventId(@PathVariable long userId, @PathVariable long eventId) {
        return eventMapper.toEventFullDto(eventService.getByUserIdAndEventId(userId, eventId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancel(@PathVariable long userId, @PathVariable long eventId) {
        Event event = eventService.cancelEvent(userId, eventId);
        return eventMapper.toEventFullDto(event);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequestsByEventId(@PathVariable long userId,
                                                                    @PathVariable long eventId) {
        return eventService.getRequestsByEventId(userId, eventId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmParticipationRequest(@PathVariable long userId,
                                                               @PathVariable long eventId,
                                                               @PathVariable long reqId) {
        return participationRequestMapper.toParticipationRequestDto(
                eventService.confirmParticipationRequest(userId, eventId, reqId)
        );
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectParticipationRequest(@PathVariable long userId,
                                                              @PathVariable long eventId,
                                                              @PathVariable long reqId) {
        return participationRequestMapper.toParticipationRequestDto(
                eventService.rejectParticipationRequest(userId, eventId, reqId)
        );
    }
}
