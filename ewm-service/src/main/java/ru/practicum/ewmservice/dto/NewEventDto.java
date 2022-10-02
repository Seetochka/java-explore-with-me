package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO для добавления нового события
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank
    @NonNull
    private String title;
    @NotBlank
    @NonNull
    private String annotation;
    @NonNull
    private Long category;
    @NotBlank
    @NonNull
    private String description;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
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
