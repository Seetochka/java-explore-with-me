package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.mapper.EventMapper;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.service.EventService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер админки отвечающий за действия с событиями
 */
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public Collection<EventFullDto> getByParams(@RequestParam(required = false) Collection<Long> users,
                                                @RequestParam(required = false) Collection<EventState> states,
                                                @RequestParam(required = false) Collection<Long> categories,
                                                @RequestParam(required = false) String rangeStart,
                                                @RequestParam(required = false) String rangeEnd,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return eventService.getByParamsForAdmin(users, states, categories, rangeStart, rangeEnd, from, size)
                .stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{eventId}")
    public EventFullDto update(@PathVariable long eventId, @RequestBody AdminUpdateEventRequestDto eventRequest)
            throws ObjectNotFountException {
        Event event = eventService.updateAdmin(eventId, eventMapper.toEventForAdminUpdate(eventRequest));

        return eventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{eventId}/publish")
    public EventFullDto publish(@PathVariable long eventId) throws ObjectNotFountException {
        Event event = eventService.publish(eventId);

        return eventMapper.toEventFullDto(event);
    }

    @PatchMapping("/{eventId}/reject")
    public EventFullDto reject(@PathVariable long eventId) throws ObjectNotFountException {
        Event event = eventService.reject(eventId);

        return eventMapper.toEventFullDto(event);
    }
}
