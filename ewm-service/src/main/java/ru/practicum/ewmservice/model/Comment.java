package ru.practicum.ewmservice.model;

import lombok.*;
import ru.practicum.ewmservice.enums.CommentStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс комментария
 */
@Entity
@Getter
@Setter
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @Column(name = "created_at")
    private LocalDateTime created;

    public void setEvent(Event event) {
        this.event = event;
        event.getComments().add(this);
    }
}
