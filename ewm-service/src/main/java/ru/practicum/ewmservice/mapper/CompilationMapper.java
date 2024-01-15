package ru.practicum.ewmservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

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
                compilation.getEvents().stream().map(eventMapper::toEventShortDto).collect(Collectors.toList())
        );
    }

    private Event toEvent(Long eventId) {
        Event event = new Event();
        event.setId(eventId);
        return event;
    }
}
