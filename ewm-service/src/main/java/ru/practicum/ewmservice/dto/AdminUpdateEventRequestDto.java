package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для редактирования события администратором
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequestDto {
    private long eventId;
    private String title;
    private String annotation;
    private long category;
    private String description;
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;

    /**
     * Широта и долгота места проведения события
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
