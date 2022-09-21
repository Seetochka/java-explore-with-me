package ru.practicum.ewmservice.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class CompilationMapper {
    public Compilation toCompilation(NewCompilationDto compilationDto) {
        return new Compilation(
                null,
                compilationDto.getTitle(),
                compilationDto.isPinned(),
                compilationDto.getEvents().stream().map(this::toEvent).collect(Collectors.toList())
        );
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents().stream().map(this::toCompilationDtoEvent).collect(Collectors.toList())
        );
    }

    private Event toEvent(Long eventId) {
        return new Event(eventId, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, 0, new ArrayList<>()
        );
    }

    private CompilationDto.Event toCompilationDtoEvent(Event event) {
        return new CompilationDto.Event(
                event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                new CompilationDto.Event.Category(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequests(),
                event.getEventDate(),
                new CompilationDto.Event.User(event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(),
                0
        );
    }
}
