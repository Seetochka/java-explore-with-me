package ru.practicum.ewmservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.dto.NewCompilationDto;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.mapper.CompilationMapper;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.service.CompilationService;

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
    public HttpStatus delete(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.delete(compId);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public HttpStatus deleteEventFromCompilation(@PathVariable long compId, @PathVariable long eventId) throws ObjectNotFountException {
        compilationService.deleteEventFromCompilation(compId, eventId);
        return HttpStatus.OK;
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public HttpStatus addEventInCompilation(@PathVariable long compId, @PathVariable long eventId) throws ObjectNotFountException {
        compilationService.addEventInCompilation(compId, eventId);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{compId}/pin")
    public HttpStatus unpinCompilation(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.unpinCompilation(compId);
        return HttpStatus.OK;
    }

    @PatchMapping("/{compId}/pin")
    public HttpStatus pinCompilation(@PathVariable long compId) throws ObjectNotFountException {
        compilationService.pinCompilation(compId);
        return HttpStatus.OK;
    }
}
