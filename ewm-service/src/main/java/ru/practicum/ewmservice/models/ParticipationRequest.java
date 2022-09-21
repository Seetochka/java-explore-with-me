package ru.practicum.ewmservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.enums.ParticipationRequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс запроса на участие в событии
 */
@Entity
@Data
@Table(name = "participation_requests")
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus status;
    @Column(name = "created_at")
    private LocalDateTime created;
}
