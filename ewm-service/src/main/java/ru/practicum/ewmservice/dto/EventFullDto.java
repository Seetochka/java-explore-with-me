package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmservice.enums.EventState;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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
    private LocalDateTime createdOn;
    private String description;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
    private User initiator;
    @NonNull
    private Location location;
    @NonNull
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Long views;
    private Collection<Comment> comments = new ArrayList<>();

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Comment {
        private Long id;
        private String content;
        private User user;
        private LocalDateTime created;
    }
}
