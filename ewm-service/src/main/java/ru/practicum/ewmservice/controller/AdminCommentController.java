package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CommentDto;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.mapper.CommentMapper;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.service.CommentService;

/**
 * Контроллер админки отвечающий за действия с комментариями
 */
@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PatchMapping("/{commentId}/publish")
    public CommentDto publish(@PathVariable long commentId) throws ObjectNotFountException {
        Comment comment = commentService.publish(commentId);
        return commentMapper.toCommentDto(comment);
    }

    @PatchMapping("/{commentId}/reject")
    public CommentDto reject(@PathVariable long commentId) throws ObjectNotFountException {
        Comment comment = commentService.reject(commentId);
        return commentMapper.toCommentDto(comment);
    }
}
