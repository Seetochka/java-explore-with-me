package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
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
    public Collection<ParticipationRequestDto> getByUserId(@PathVariable long userId) {
        return participationRequestService.getByUserId(userId)
                .stream()
                .map(participationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto create(@PathVariable long userId,
                                          @RequestParam long eventId) {
        ParticipationRequest participationRequest = participationRequestService.create(userId, eventId);
        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        ParticipationRequest participationRequest = participationRequestService.cancelParticipationRequest(userId,
                requestId);
        return participationRequestMapper.toParticipationRequestDto(participationRequest);
    }
}
