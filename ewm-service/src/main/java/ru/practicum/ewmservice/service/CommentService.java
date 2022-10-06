package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.model.Comment;

public interface CommentService {

    Comment create(long userId, long eventId, Comment comment);

    void delete(long userId, long commentId);

    Comment publish(long commentId);

    Comment reject(long commentId);

    Comment getById(long id);
}
