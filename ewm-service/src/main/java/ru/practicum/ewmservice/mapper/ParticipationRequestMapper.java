package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.ParticipationRequestDto;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.ParticipationRequest;
import ru.practicum.ewmservice.model.User;

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
        Event event = new Event();
        event.setId(participationRequestDto.getEvent());
        User user = new User();
        user.setId(participationRequestDto.getRequester());
        return new ParticipationRequest(
                participationRequestDto.getId(),
                event,
                user,
                participationRequestDto.getStatus(),
                participationRequestDto.getCreated()
        );
    }
}
