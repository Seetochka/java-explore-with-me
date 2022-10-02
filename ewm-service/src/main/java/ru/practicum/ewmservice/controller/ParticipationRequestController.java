package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.exception.EventParticipantLimitException;
import ru.practicum.ewmservice.exception.EventStateException;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.mapper.ParticipationRequestMapper;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.service.ParticipationRequestService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер отвечающий за действия с заявками на участие
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class ParticipationRequestController {
    private final ParticipationRequestService participationRequestService;
    private final ParticipationRequestMapper participationRequestMapper;

    @GetMapping("/users/{userId}/requests")
    public Collection<ParticipationRequestDto> getByUserId(@PathVariable long userId) throws ObjectNotFountException {
        return participationRequestService.getByUserId(userId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable long userId,
                                          @RequestParam long eventId)
            throws ObjectNotFountException, EventStateException, UserHaveNoRightsException, EventParticipantLimitException {
        ParticipationRequest participationRequest = participationRequestService.create(userId, eventId);

        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable long userId, @PathVariable long requestId)
            throws ObjectNotFountException {
        ParticipationRequest participationRequest = participationRequestService.cancelParticipationRequest(userId,
                requestId);

        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }
}