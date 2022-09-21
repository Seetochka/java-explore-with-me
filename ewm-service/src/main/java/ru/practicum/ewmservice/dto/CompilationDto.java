package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * DTO подборок
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NonNull
    private Long id;
    @NotBlank
    @NonNull
    private String title;
    private boolean pinned;
    private Collection<Event> events;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Event {
        private Long id;
        private String title;
        private String annotation;
        private Category category;
        private Integer confirmedRequests;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
