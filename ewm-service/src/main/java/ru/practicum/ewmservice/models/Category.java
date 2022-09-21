package ru.practicum.ewmservice.models;

import lombok.*;

import javax.persistence.*;

/**
 * Класс категории
 */
@Entity
@Getter
@Setter
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
