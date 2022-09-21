package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmservice.enums.EventState;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO события
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;
    @NotBlank
    @NonNull
    private String title;
    @NotBlank
    @NonNull
    private String annotation;
    @NonNull
    private Category category;
    private Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NonNull
    private User initiator;
    @NonNull
    private Location location;
    @NonNull
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Long views;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private Long id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private Float lat;
        private Float lon;
    }
}
