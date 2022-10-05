package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.AdminUpdateEventRequestDto;
import ru.practicum.ewmservice.dto.EventFullDto;
import ru.practicum.ewmservice.dto.EventShortDto;
import ru.practicum.ewmservice.dto.NewEventDto;
import ru.practicum.ewmservice.dto.UpdateEventRequestDto;
import ru.practicum.ewmservice.model.Category;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.Event;

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
        Event event = new Event();
        event.setId(eventRequest.getEventId());
        event.setTitle(eventRequest.getTitle());
        event.setAnnotation(eventRequest.getAnnotation());
        event.setCategory(new Category(eventRequest.getCategory(), null));
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setLat(Optional.ofNullable(eventRequest.getLocation()).map(AdminUpdateEventRequestDto.Location::getLat).orElse(null));
        event.setLon(Optional.ofNullable(eventRequest.getLocation()).map(AdminUpdateEventRequestDto.Location::getLon).orElse(null));
        event.setPaid(eventRequest.isPaid());
        event.setParticipantLimit(eventRequest.getParticipantLimit());
        event.setRequestModeration(eventRequest.isRequestModeration());
        return event;
    }

    public Event toEventForUpdate(UpdateEventRequestDto eventRequest) {
        Event event = new Event();
        event.setId(eventRequest.getEventId());
        event.setTitle(eventRequest.getTitle());
        event.setAnnotation(eventRequest.getAnnotation());
        event.setCategory(new Category(eventRequest.getCategory(), null));
        event.setDescription(eventRequest.getDescription());
        event.setEventDate(eventRequest.getEventDate());
        event.setPaid(eventRequest.isPaid());
        event.setParticipantLimit(eventRequest.getParticipantLimit());
        return event;
    }

    public Event toEventForCreate(NewEventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setAnnotation(eventDto.getAnnotation());
        event.setCategory(new Category(eventDto.getCategory(), null));
        event.setDescription(eventDto.getDescription());
        event.setEventDate(eventDto.getEventDate());
        event.setLat(eventDto.getLocation().getLat());
        event.setLon(eventDto.getLocation().getLon());
        event.setPaid(eventDto.isPaid());
        event.setParticipantLimit(eventDto.getParticipantLimit());
        event.setRequestModeration(eventDto.isRequestModeration());
        return event;
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
