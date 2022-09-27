package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.practicum.ewmservice.client.EventClient;
import ru.practicum.ewmservice.dto.ViewStats;
import ru.practicum.ewmservice.enums.EventSort;
import ru.practicum.ewmservice.enums.EventState;
import ru.practicum.ewmservice.enums.ParticipationRequestStatus;
import ru.practicum.ewmservice.exception.EventStateException;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.service.CategoryService;
import ru.practicum.ewmservice.service.ParticipationRequestService;
import ru.practicum.ewmservice.service.UserService;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.trait.DateTimeConverterTrait;
import ru.practicum.ewmservice.trait.PageTrait;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис событий
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService, PageTrait, DateTimeConverterTrait {
    private final UserService userService;
    private final CategoryService categoryService;
    private final ParticipationRequestService participationRequestService;

    private final EventRepository eventRepository;
    private final ParticipationRequestsRepository participationRequestsRepository;

    private final EventClient eventClient;

    @Override
    @Transactional
    public Event create(long userId, Event event) throws ObjectNotFountException {
        if (!categoryService.checkExistsById(event.getCategory().getId())) {
            throw new ObjectNotFountException(
                    String.format("Категория с id %d не существует", event.getCategory().getId()),
                    "CreateEvent"
            );
        }

        User user = userService.getById(userId);

        event.setInitiator(user);
        event.setState(EventState.PENDING);
        event.setPublishedOn(null);
        event.setCreatedAt(LocalDateTime.now());
        event.setUpdatedAt(LocalDateTime.now());

        event = eventRepository.save(event);

        log.info("CreateEvent. Создано событие с id {}", event.getId());
        return event;
    }

    @Override
    @Transactional
    public Event updateAdmin(long eventId, Event event) throws ObjectNotFountException {
        if (!categoryService.checkExistsById(event.getCategory().getId())) {
            throw new ObjectNotFountException(
                    String.format("Категория с id %d не существует", event.getCategory().getId()),
                    "UpdateEventAdmin"
            );
        }

        Event eventUpdated = getById(event.getId());

        eventUpdated.setTitle(event.getTitle());
        eventUpdated.setAnnotation(event.getAnnotation());
        eventUpdated.setCategory(event.getCategory());
        eventUpdated.setDescription(event.getDescription());
        eventUpdated.setEventDate(event.getEventDate());
        eventUpdated.setLat(event.getLat());
        eventUpdated.setLon(event.getLon());
        eventUpdated.setPaid(event.getPaid());
        eventUpdated.setParticipantLimit(event.getParticipantLimit());
        eventUpdated.setRequestModeration(event.getRequestModeration());
        eventUpdated.setUpdatedAt(LocalDateTime.now());

        eventUpdated = eventRepository.save(eventUpdated);

        log.info("UpdateEventAdmin. Обновлено событие с id {}", event.getId());
        return eventUpdated;
    }

    @Override
    @Transactional
    public Event updateUser(long userId, Event event) throws ObjectNotFountException, UserHaveNoRightsException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "UpdateEventUser"
            );
        }

        if (!categoryService.checkExistsById(event.getCategory().getId())) {
            throw new ObjectNotFountException(
                    String.format("Категория с id %d не существует", event.getCategory().getId()),
                    "UpdateEventUser"
            );
        }

        Event eventUpdated = getById(event.getId());

        if (userId != eventUpdated.getInitiator().getId()) {
            throw new UserHaveNoRightsException(
                    String.format("Пользователь с id %d не имеет прав редактировать событие с id %d", userId,
                            event.getId()),
                    "UpdateEventUser"
            );
        }

        eventUpdated.setTitle(event.getTitle());
        eventUpdated.setAnnotation(event.getAnnotation());
        eventUpdated.setCategory(event.getCategory());
        eventUpdated.setDescription(event.getDescription());
        eventUpdated.setEventDate(event.getEventDate());
        eventUpdated.setPaid(event.getPaid());
        eventUpdated.setParticipantLimit(event.getParticipantLimit());
        eventUpdated.setUpdatedAt(LocalDateTime.now());

        eventUpdated = eventRepository.save(eventUpdated);

        log.info("UpdateEventUser. Обновлено событие с id {}", event.getId());
        return eventUpdated;
    }

    @Override
    @Transactional
    public Event publish(long eventId) throws ObjectNotFountException {
        Event event = getById(eventId);

        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());

        event = eventRepository.save(event);

        log.info("PublishEvent. Опубликовано событие с id {}", event.getId());
        return event;
    }

    @Override
    @Transactional
    public Event reject(long eventId) throws ObjectNotFountException {
        Event event = getById(eventId);

        event.setState(EventState.CANCELED);

        event = eventRepository.save(event);

        log.info("RejectEvent. Отклонено событие с id {}", event.getId());
        return event;
    }

    @Override
    @Transactional
    public Event cancelEvent(long userId, long eventId) throws ObjectNotFountException, UserHaveNoRightsException,
            EventStateException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "CancelEvent"
            );
        }

        Event event = getById(eventId);

        if (userId != event.getInitiator().getId()) {
            throw new UserHaveNoRightsException(
                    String.format("Пользователь с id %d не имеет прав редактировать событие с id %d", userId,
                            event.getId()),
                    "CancelEvent"
            );
        }

        if (!event.getState().equals(EventState.PENDING)) {
            throw new EventStateException(
                    String.format("Событие с id %d не находится в статусе ожидания модерации, его нельзя отменить",
                            eventId),
                    "CancelEvent"
            );
        }

        event.setState(EventState.CANCELED);

        event = eventRepository.save(event);

        log.info("CancelEvent. Отменено событие с id {}", event.getId());
        return event;
    }

    @Override
    @Transactional
    public ParticipationRequest confirmParticipationRequest(long userId, long eventId, long reqId)
            throws ObjectNotFountException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "ConfirmParticipationRequest"
            );
        }

        if (!checkExistsById(eventId)) {
            throw new ObjectNotFountException(
                    String.format("Событие с id %d не существует", eventId),
                    "ConfirmParticipationRequest"
            );
        }

        ParticipationRequest participationRequest = participationRequestService.getById(reqId);

        participationRequest.setStatus(ParticipationRequestStatus.CONFIRMED);

        participationRequest = participationRequestsRepository.save(participationRequest);

        log.info("ConfirmParticipationRequest. Подтверждение заявки на участии с id {}", reqId);
        return participationRequest;
    }

    @Override
    @Transactional
    public ParticipationRequest rejectParticipationRequest(long userId, long eventId, long reqId)
            throws ObjectNotFountException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "RejectParticipationRequest"
            );
        }

        if (!checkExistsById(eventId)) {
            throw new ObjectNotFountException(
                    String.format("Событие с id %d не существует", eventId),
                    "RejectParticipationRequest"
            );
        }

        ParticipationRequest participationRequest = participationRequestService.getById(reqId);

        participationRequest.setStatus(ParticipationRequestStatus.REJECTED);

        participationRequest = participationRequestsRepository.save(participationRequest);

        log.info("RejectParticipationRequest. Отклонение заявки на участии с id {}", reqId);
        return participationRequest;
    }

    @Override
    public Collection<Event> getByParamsForAdmin(Collection<Long> users, Collection<EventState> states,
                                                 Collection<Long> categories, String rangeStart,
                                                 String rangeEnd, int from, int size) {
        LocalDateTime start = convertStringToLocalDateTime(rangeStart, null);
        LocalDateTime end = convertStringToLocalDateTime(rangeEnd, null);

        Pageable page = getPage(from, size, "id", Sort.Direction.ASC);
        Specification<Event> specification = null;

        if (users != null && !users.isEmpty()) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.in(root.get("initiator").get("id")).value(users)
            );
        }

        if (users != null && !users.isEmpty()) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.get("state")).value(states)
            );
        }

        if (categories != null && !categories.isEmpty()) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.in(root.get("category").get("id")).value(categories)
            );
        }

        if (start != null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.greaterThan(root.get("eventDate").as(LocalDateTime.class), start)
            );
        }

        if (end != null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.lessThan(root.get("eventDate").as(LocalDateTime.class), end)
            );
        }

        return eventRepository.findAll(specification, page)
                .stream()
                .map(this::prepareEvent)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Event> getByParamsForUser(String text, Collection<Long> categories, Boolean paid,
                                                String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                                EventSort sort, int from, int size) {
        LocalDateTime start = convertStringToLocalDateTime(rangeStart, null);
        LocalDateTime end = convertStringToLocalDateTime(rangeEnd, null);

        Specification<Event> specification = Specification.where(
                (root, criteriaQuery, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("state"), EventState.PUBLISHED)
        );

        if (StringUtils.hasText(text)) {
            Specification<Event> specificationSearch = Specification.where(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("annotation"), "%" + text + "%")
            );

            specificationSearch = Specification.where(specificationSearch)
                    .or(
                            (root, criteriaQuery, criteriaBuilder) ->
                                    criteriaBuilder.like(root.get("description"), "%" + text + "%")
                    );

            specification = Specification.where(specification)
                    .and(specificationSearch);
        }

        if (categories != null && !categories.isEmpty()) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.in(root.get("category").get("id")).value(categories)
            );
        }

        if (paid != null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("paid"), paid)
            );
        }

        if (start != null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.greaterThan(root.get("eventDate").as(LocalDateTime.class), start)
            );
        }

        if (end != null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.lessThan(root.get("eventDate").as(LocalDateTime.class), end)
            );
        }

        if (start == null && end == null) {
            specification = Specification.where(specification).and(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.greaterThan(root.get("eventDate").as(LocalDateTime.class),
                                    LocalDateTime.now())
            );
        }

        if (onlyAvailable != null) {
            Specification<Event> specificationAvailable = Specification.where(
                    (root, criteriaQuery, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("participantLimit"), 0)
            );

            specificationAvailable = Specification.where(specificationAvailable)
                    .or(
                            (root, criteriaQuery, criteriaBuilder) -> {
                                Subquery<Long> sub = criteriaQuery.subquery(Long.class);
                                Root<ParticipationRequest> subRoot = sub.from(ParticipationRequest.class);
                                Join<ParticipationRequest, Event> subEvents = subRoot.join("event");
                                sub.select(criteriaBuilder.count(subRoot.get("id")));
                                sub.where(criteriaBuilder.equal(root.get("id"), subEvents.get("id")));

                                return criteriaBuilder.lessThan(sub, root.get("participantLimit"));
                            }
                    );

            specification = Specification.where(specification)
                    .and(specificationAvailable);
        }

        Collection<Event> result = eventRepository.findAll(specification)
                .stream()
                .map(this::prepareEvent)
                .collect(Collectors.toList());

        if (sort.equals(EventSort.EVENT_DATE)) {
            return result.stream()
                    .sorted(Comparator.comparing(Event::getViews))
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        }

        if (sort.equals(EventSort.VIEWS)) {
            return result.stream()
                    .sorted(Comparator.comparing(Event::getEventDate))
                    .skip(from)
                    .limit(size)
                    .collect(Collectors.toList());
        }

        return result.stream()
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public Event getById(long id) throws ObjectNotFountException {
        Event event = eventRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", id),
                "GetEventById"
        ));

        return prepareEvent(event);
    }

    @Override
    public Collection<Event> getByUserId(long userId, int from, int size) throws ObjectNotFountException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "GetByUserIdAndEventId"
            );
        }

        Pageable page = getPage(from, size, "id", Sort.Direction.ASC);

        return eventRepository.findAllByInitiatorId(userId, page)
                .stream()
                .map(this::prepareEvent)
                .collect(Collectors.toList());
    }

    @Override
    public Event getByUserIdAndEventId(long userId, long eventId) throws ObjectNotFountException,
            UserHaveNoRightsException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "GetByUserIdAndEventId"
            );
        }

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", eventId),
                "GetByUserIdAndEventId"
        ));

        if (userId != event.getInitiator().getId()) {
            throw new UserHaveNoRightsException(
                    String.format("Пользователь с id %d не имеет прав редактировать событие с id %d", userId,
                            event.getId()),
                    "GetByUserIdAndEventId"
            );
        }

        return event;
    }

    @Override
    public Collection<ParticipationRequest> getRequestsByEventId(long userId, long eventId)
            throws ObjectNotFountException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "GetRequestsByEventId"
            );
        }

        if (!checkExistsById(eventId)) {
            throw new ObjectNotFountException(
                    String.format("Событие с id %d не существует", eventId),
                    "GetRequestsByEventId"
            );
        }

        return participationRequestsRepository.findAllByEventId(eventId);
    }

    @Override
    public boolean checkExistsById(long id) {
        return eventRepository.existsById(id);
    }

    private Event prepareEvent(Event event) {
        Collection<ViewStats> viewStats = eventClient.getStats(List.of("/events/" + event.getId()));

        if (!viewStats.isEmpty()) {
            event.setViews(
                    viewStats.stream()
                            .findFirst()
                            .get()
                            .getHits()
            );
        }

        event.setConfirmedRequests(participationRequestService.getCountParticipantByEventId(event.getId()));

        return event;
    }
}
