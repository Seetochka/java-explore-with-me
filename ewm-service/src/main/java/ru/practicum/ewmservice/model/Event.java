package ru.practicum.ewmservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.practicum.ewmservice.enums.EventState;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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

    @OneToMany()
    @JoinColumn(name = "event_id")
    private Collection<Comment> comments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
