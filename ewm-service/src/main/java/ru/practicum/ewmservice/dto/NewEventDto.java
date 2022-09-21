package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO события
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NonNull
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
