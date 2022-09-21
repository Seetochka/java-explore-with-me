package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.*;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.Event;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    public EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                new EventFullDto.Category(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getCreatedAt(),
                event.getDescription(),
                event.getEventDate(),
                new EventFullDto.User(event.getInitiator().getId(), event.getInitiator().getName()),
                new EventFullDto.Location(event.getLat(), event.getLon()),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getViews(),
                event.getComments().stream().map(this::toEventFullDtoComment).collect(Collectors.toList())
        );
    }

    public EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                new EventShortDto.Category(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                new EventShortDto.User(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                event.getViews()
        );
    }

    public Event toEventForAdminUpdate(AdminUpdateEventRequestDto eventRequest) {
        return new Event(
                eventRequest.getEventId(),
                eventRequest.getTitle(),
                eventRequest.getAnnotation(),
                new Category(eventRequest.getCategory(), null),
                eventRequest.getDescription(),
                eventRequest.getEventDate(),
                Optional.ofNullable(eventRequest.getLocation()).map(AdminUpdateEventRequestDto.Location::getLat).orElse(null),
                Optional.ofNullable(eventRequest.getLocation()).map(AdminUpdateEventRequestDto.Location::getLon).orElse(null),
                eventRequest.isPaid(),
                eventRequest.getParticipantLimit(),
                eventRequest.isRequestModeration(),
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                new ArrayList<>()
        );
    }

    public Event toEventForUpdate(UpdateEventRequestDto eventRequest) {
        return new Event(
                eventRequest.getEventId(),
                eventRequest.getTitle(),
                eventRequest.getAnnotation(),
                new Category(eventRequest.getCategory(), null),
                eventRequest.getDescription(),
                eventRequest.getEventDate(),
                null,
                null,
                eventRequest.isPaid(),
                eventRequest.getParticipantLimit(),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                new ArrayList<>()
        );
    }

    public Event toEventForCreate(NewEventDto eventDto) {
        return new Event(
                null,
                eventDto.getTitle(),
                eventDto.getAnnotation(),
                new Category(eventDto.getCategory(), null),
                eventDto.getDescription(),
                eventDto.getEventDate(),
                eventDto.getLocation().getLat(),
                eventDto.getLocation().getLon(),
                eventDto.isPaid(),
                eventDto.getParticipantLimit(),
                eventDto.isRequestModeration(),
                null,
                null,
                null,
                null,
                null,
                null,
                0,
                new ArrayList<>()
        );
    }

    private EventFullDto.Comment toEventFullDtoComment(Comment comment) {
        return new EventFullDto.Comment(
                comment.getId(),
                comment.getContent(),
                new EventFullDto.User(comment.getUser().getId(), comment.getUser().getName()),
                comment.getCreated()
        );
    }
}
