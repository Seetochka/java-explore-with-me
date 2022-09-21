package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.Event;

@Component
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getStatus(),
                new CommentDto.User(comment.getUser().getId(), comment.getUser().getName()),
                toCommentDtoEvent(comment.getEvent()),
                comment.getCreated()
        );
    }

    public Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getContent(),
                commentDto.getStatus(),
                null,
                null,
                null
        );
    }

    private CommentDto.Event toCommentDtoEvent(Event event) {
        return new CommentDto.Event(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                new CommentDto.Event.Category(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                new CommentDto.Event.User(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                0
        );
    }
}
