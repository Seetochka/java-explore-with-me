package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.mapper.CommentMapper;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.service.CommentService;

import javax.validation.Valid;

/**
 * Контроллер отвечающий за действия с комментариями
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    public CommentDto create(@PathVariable long userId, @PathVariable long eventId,
                             @Valid @RequestBody CommentDto commentDto) throws ObjectNotFountException {
        Comment category = commentService.create(userId, eventId, commentMapper.toComment(commentDto));
        return commentMapper.toCommentDto(category);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public HttpStatus delete(@PathVariable long userId, @PathVariable long commentId)
            throws ObjectNotFountException, UserHaveNoRightsException {
        commentService.delete(userId, commentId);
        return HttpStatus.OK;
    }
}
