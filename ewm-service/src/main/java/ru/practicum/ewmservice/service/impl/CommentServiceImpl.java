package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.enums.CommentStatus;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.model.Comment;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.repository.CommentRepository;
import ru.practicum.ewmservice.service.CommentService;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.service.UserService;

import java.time.LocalDateTime;

/**
 * Сервис комментариев
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final UserService userService;
    private final EventService eventService;


    @Override
    @Transactional
    public Comment create(long userId, long eventId, Comment comment) throws ObjectNotFountException {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", eventId),
                "GetEventById"
        ));
        comment.setUser(user);
        comment.setStatus(CommentStatus.PENDING);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        comment = commentRepository.save(comment);
        log.info("CreateComment. Создан комментарий с id {}", comment.getId());
        return comment;
    }

    @Override
    @Transactional
    public void delete(long userId, long commentId) throws ObjectNotFountException, UserHaveNoRightsException {
        if (!userService.checkExistsById(userId)) {
            throw new ObjectNotFountException(
                    String.format("Пользователь с id %d не существует", userId),
                    "DeleteComment"
            );
        }

        Comment comment = getById(commentId);
        if (userId != comment.getUser().getId()) {
            throw new UserHaveNoRightsException(
                    String.format("Пользователь с id %d не имеет прав удалять комментарий с id %d", userId,
                            comment.getId()),
                    "DeleteComment"
            );
        }

        commentRepository.deleteById(comment.getId());
        log.info("DeleteComment. Удален комментарий с id {}", comment.getId());
    }

    @Override
    @Transactional
    public Comment publish(long commentId) throws ObjectNotFountException {
        Comment comment = getById(commentId);
        comment.setStatus(CommentStatus.PUBLISHED);
        comment = commentRepository.save(comment);
        log.info("PublishComment. Опубликован комментарий с id {}", comment.getId());
        return comment;
    }

    @Override
    @Transactional
    public Comment reject(long commentId) throws ObjectNotFountException {
        Comment comment = getById(commentId);
        comment.setStatus(CommentStatus.CANCELED);
        comment = commentRepository.save(comment);
        log.info("RejectComment. Отменен комментарий с id {}", comment.getId());
        return comment;
    }

    @Override
    public Comment getById(long id) throws ObjectNotFountException {
        return commentRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Комменарий с id %d не существует", id),
                "GetCommentById"
        ));
    }
}
