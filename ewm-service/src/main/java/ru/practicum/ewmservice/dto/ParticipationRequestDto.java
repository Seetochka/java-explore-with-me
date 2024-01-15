package ru.practicum.ewmservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmservice.enums.ParticipationRequestStatus;

import java.time.LocalDateTime;

/**
 * DTO заявки на участие в событии
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private Long id;
    private long event;
    private long requester;
    private ParticipationRequestStatus status;
    private LocalDateTime created;
}
