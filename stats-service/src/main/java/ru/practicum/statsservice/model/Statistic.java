package ru.practicum.statsservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс статистики
 */
@Entity
@Getter
@Setter
@Table(name = "stats")
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @Column(name = "created_at")
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Statistic statistic = (Statistic) o;
        return id != null && Objects.equals(id, statistic.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
