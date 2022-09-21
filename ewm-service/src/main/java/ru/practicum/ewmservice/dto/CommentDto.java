package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmservice.enums.CommentStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank
    @NonNull
    private String content;
    private CommentStatus status;
    private User user;
    private Event event;
    private LocalDateTime created;

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
    public static class Event {
        private Long id;
        private String title;
        private String annotation;
        private Category category;
        private Integer confirmedRequests;
        private LocalDateTime eventDate;
        private User initiator;
        private boolean paid;
        private long views;

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
    }
}
