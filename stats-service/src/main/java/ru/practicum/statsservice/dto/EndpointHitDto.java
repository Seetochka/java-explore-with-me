package ru.practicum.statsservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.statsservice.traits.DateTimeConverterTrait;

import java.time.LocalDateTime;

/**
 * DTO просмотра
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto implements DateTimeConverterTrait {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DEFAULT_PATTERN_DATE_TIME)
    private LocalDateTime timestamp;
}
