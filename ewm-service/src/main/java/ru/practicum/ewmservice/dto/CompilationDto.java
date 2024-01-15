package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

/**
 * DTO подборки
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
    private Collection<EventShortDto> events;
}
