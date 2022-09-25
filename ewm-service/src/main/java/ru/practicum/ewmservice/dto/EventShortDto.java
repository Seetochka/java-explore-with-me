package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmservice.traits.DateTimeConverterTrait;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO с краткой информацией о событии
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto implements DateTimeConverterTrait {
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
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_PATTERN_DATE_TIME)
    private LocalDateTime eventDate;
    @NonNull
    private User initiator;
    private boolean paid;
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
}
