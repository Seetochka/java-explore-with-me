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
    private CategoryDto category;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @NonNull
    private LocalDateTime eventDate;
    @NonNull
    private UserShortDto initiator;
    @NonNull
    private Location location;
    @NonNull
    private Boolean paid;
    private Integer participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Long views;
    private Collection<CommentDto> comments = new ArrayList<>();
}
