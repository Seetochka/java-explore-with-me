package ru.practicum.ewmservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmservice.traits.DateTimeConverterTrait;

import java.time.LocalDateTime;

/**
 * DTO для редактирования события пользователем
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto implements DateTimeConverterTrait {
    @NonNull
    private Long eventId;
    private String title;
    private String annotation;
    private long category;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_PATTERN_DATE_TIME)
    private LocalDateTime eventDate;
    private boolean paid;
    private int participantLimit;
}
