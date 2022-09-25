package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.traits.DateTimeConverterTrait;

import java.time.LocalDateTime;

/**
 * DTO для редактирования события администратором
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateEventRequestDto implements DateTimeConverterTrait {
    private long eventId;
    private String title;
    private String annotation;
    private long category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_PATTERN_DATE_TIME)
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
