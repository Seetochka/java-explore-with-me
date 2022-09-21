package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO события
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
