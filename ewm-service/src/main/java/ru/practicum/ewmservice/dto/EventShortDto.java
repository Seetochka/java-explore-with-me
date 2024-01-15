package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO с краткой информацией о событии
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    @NotBlank
    @NonNull
    private String title;
    @NotBlank
    @NonNull
    private String annotation;
    @NonNull
    private CategoryDto category;
    private Integer confirmedRequests;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
    private UserShortDto initiator;
    private boolean paid;
    private Long views;
}
