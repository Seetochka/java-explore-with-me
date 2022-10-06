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
    private UserShortDto user;
    private EventShortDto event;
    private LocalDateTime created;
}
