package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.mappers.CompilationMapper;
import ru.practicum.ewmservice.models.Compilation;
import ru.practicum.ewmservice.services.CompilationService;

import javax.validation.Valid;

/**
 * Контроллер админки отвечающий за действия с подборками
 */
@RestController
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    public CompilationDto create(@Valid @RequestBody NewCompilationDto compilationDto) throws ObjectNotFountException {
        Compilation compilation = compilationService.create(compilationMapper.toCompilation(compilationDto));

        return compilationMapper.toCompilationDto(compilation);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId, @PathVariable long eventId) throws ObjectNotFountException {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable long compId, @PathVariable long eventId) throws ObjectNotFountException {
        compilationService.addEventInCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.pinCompilation(compId);
    }
}
