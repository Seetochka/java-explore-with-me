package ru.practicum.ewmservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.dto.CompilationDto;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.mappers.CompilationMapper;
import ru.practicum.ewmservice.services.CompilationService;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Контроллер отвечающий за действия с подборками
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping("/compilations")
    public Collection<CompilationDto> getByPinned(@RequestParam boolean pinned,
                                                  @RequestParam(defaultValue = "0") int from,
                                                  @RequestParam(defaultValue = "10") int size) {
        return compilationService.getByPinned(pinned, from, size)
                .stream()
                .map(compilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getById(@PathVariable long compId) throws ObjectNotFountException {
        return compilationMapper.toCompilationDto(compilationService.getById(compId));
    }
}
