package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.Comment;

public interface CommentService {

    Comment create(long userId, long eventId, Comment comment) throws ObjectNotFountException;

    void delete(long userId, long commentId) throws ObjectNotFountException, UserHaveNoRightsException;

    Comment publish(long commentId) throws ObjectNotFountException;

    Comment reject(long commentId) throws ObjectNotFountException;

    Comment getById(long id) throws ObjectNotFountException;
}
