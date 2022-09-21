package ru.practicum.ewmservice.models;

import lombok.*;
import ru.practicum.ewmservice.enums.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Класс события
 */
@Entity
@Getter
@Setter
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String annotation;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    private String description;
    private LocalDateTime eventDate;
    private Float lat;
    private Float lon;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @ManyToOne()
    @JoinColumn(name = "initiator_id")
    private User initiator;
    private LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    private EventState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Transient
    private Integer confirmedRequests;
    @Transient
    private long views;
}
