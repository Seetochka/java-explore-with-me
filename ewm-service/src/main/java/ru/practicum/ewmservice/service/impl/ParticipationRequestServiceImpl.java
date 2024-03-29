package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.enums.ParticipationRequestStatus;
import ru.practicum.ewmservice.exception.EventParticipantLimitException;
import ru.practicum.ewmservice.exception.EventStateException;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.service.UserService;
import ru.practicum.ewmservice.service.ParticipationRequestService;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Сервис запроса на участие
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final UserService userService;

    private final ParticipationRequestsRepository participationRequestsRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public ParticipationRequest create(long userId, long eventId) {
        User user = userService.getById(userId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", eventId),
                "CreateParticipationRequest"
        ));
        if (userId == event.getInitiator().getId()) {
            throw new UserHaveNoRightsException(
                    String.format("Пользователь с id %d не может добавить запрос на участие в своем событии", userId),
                    "CreateParticipationRequest"
            );
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new EventStateException(
                    String.format("Событие с id %d не опубликовано, нельзя участвовать в неопубликованном событии",
                            eventId),
                    "CreateParticipationRequest"
            );
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == getCountParticipantByEventId(eventId)) {
            throw new EventParticipantLimitException(
                    String.format("У события с id %d достигнут лимит участников",
                            eventId),
                    "CreateParticipationRequest"
            );
        }

        ParticipationRequest participationRequest = new ParticipationRequest();
        if (event.getRequestModeration()) {
            participationRequest.setStatus(ParticipationRequestStatus.PENDING);
        } else {
            participationRequest.setStatus(ParticipationRequestStatus.CONFIRMED);
        }

        participationRequest.setRequester(user);
        participationRequest.setEvent(event);
        participationRequest.setCreated(LocalDateTime.now());
        participationRequest = participationRequestsRepository.save(participationRequest);
        log.info("CreateParticipationRequest. Создан запрос на участие с id {}", participationRequest.getId());
        return participationRequest;
    }

    @Override
    @Transactional
    public ParticipationRequest cancelParticipationRequest(long userId, long requestId) {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "CancelParticipationRequest"
            );
        }

        if (!checkExistsById(requestId)) {
            throw new ObjectNotFountException(
                    String.format("Запрос на участие с id %d не существует", userId),
                    "CancelParticipationRequest"
            );
        }

        ParticipationRequest participationRequest = getById(requestId);
        participationRequest.setStatus(ParticipationRequestStatus.CANCELED);
        participationRequest = participationRequestsRepository.save(participationRequest);
        log.info("CancelParticipationRequest. Отменен запрос на участие с id {}", participationRequest.getId());
        return participationRequest;
    }

    @Override
    public ParticipationRequest getById(long id) {
        return participationRequestsRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Запрос на участие с id %d не существует", id),
                "GetParticipationRequestById"
        ));
    }

    @Override
    public Collection<ParticipationRequest> getByUserId(long userId) {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "GetParticipationRequestByUserId"
            );
        }

        return participationRequestsRepository.findAllByRequesterId(userId);
    }

    @Override
    public int getCountParticipantByEventId(long eventId) {
        return participationRequestsRepository.countByEventIdAndStatus(eventId, ParticipationRequestStatus.CONFIRMED);
    }

    @Override
    public boolean checkExistsById(long id) {
        return participationRequestsRepository.existsById(id);
    }
}
