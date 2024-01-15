package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.model.ParticipationRequest;

@Component
public class ParticipationRequestMapper {
    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequests) {
        return new ParticipationRequestDto(
                participationRequests.getId(),
                participationRequests.getEvent().getId(),
                participationRequests.getRequester().getId(),
                participationRequests.getStatus(),
                participationRequests.getCreated()
        );
    }
}
