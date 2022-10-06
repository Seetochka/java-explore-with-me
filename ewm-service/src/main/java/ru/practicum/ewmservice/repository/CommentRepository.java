package ru.practicum.ewmservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmservice.enums.CommentStatus;
import ru.practicum.ewmservice.model.Comment;

import java.util.List;

/**
 * Интерфейс репозитория комментариев
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByStatusAndEventId(CommentStatus status, long eventId);
}
