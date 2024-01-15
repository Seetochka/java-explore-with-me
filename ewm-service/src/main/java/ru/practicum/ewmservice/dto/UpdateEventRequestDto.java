package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * DTO для редактирования события пользователем
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {
    @NonNull
    private Long eventId;
    private String title;
    private String annotation;
    private long category;
    private String description;
    private LocalDateTime eventDate;
    private boolean paid;
    private int participantLimit;
}
