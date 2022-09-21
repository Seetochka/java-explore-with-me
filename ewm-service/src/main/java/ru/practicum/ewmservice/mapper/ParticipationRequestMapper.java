package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.User;

import java.util.ArrayList;

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
                        null, null, null, null, null, null, null, 0, new ArrayList<>()),
                new User(participationRequestDto.getRequester(), null, null),
                participationRequestDto.getStatus(),
                participationRequestDto.getCreated()
        );
    }
}
