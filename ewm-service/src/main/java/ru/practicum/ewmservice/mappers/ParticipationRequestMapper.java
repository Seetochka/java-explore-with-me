package ru.practicum.ewmservice.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.models.Event;
import ru.practicum.ewmservice.models.ParticipationRequest;
import ru.practicum.ewmservice.models.User;

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

    public ParticipationRequest toParticipationRequest(ParticipationRequestDto participationRequestDto) {
        return new ParticipationRequest(
                participationRequestDto.getId(),
                new Event(participationRequestDto.getEvent(), null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, 0),
                new User(participationRequestDto.getRequester(), null, null),
                participationRequestDto.getStatus(),
                participationRequestDto.getCreated()
        );
    }
}
