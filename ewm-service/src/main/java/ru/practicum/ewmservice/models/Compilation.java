package ru.practicum.ewmservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Класс подборки
 */
@Entity
@Getter
@Setter
@Table(name = "compilations")
@AllArgsConstructor
@NoArgsConstructor
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Boolean pinned;
    @ManyToMany
    @JoinTable(name = "compilation_event",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Collection<Event> events = new ArrayList<>();
}
