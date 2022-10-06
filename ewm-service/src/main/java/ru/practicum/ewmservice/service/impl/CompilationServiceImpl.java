package ru.practicum.ewmservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.model.Compilation;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.repository.CompilationRepository;
import ru.practicum.ewmservice.service.CompilationService;
import ru.practicum.ewmservice.service.EventService;
import ru.practicum.ewmservice.trait.PageTrait;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Сервис подборок
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService, PageTrait {
    private final CompilationRepository compilationRepository;

    private final EventService eventService;

    @Override
    @Transactional
    public Compilation create(Compilation compilation) {
        Collection<Event> events = new ArrayList<>();

        for (Event event : compilation.getEvents()) {
            events.add(eventService.getByIdOrThrow(event.getId()));
        }

        compilation.setEvents(events);
        compilation = compilationRepository.save(compilation);
        log.info("CreateCompilation. Создана подборка с id {}", compilation.getId());
        return compilation;
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (!checkExistsById(id)) {
            throw new ObjectNotFountException(
                    String.format("Подборки с id %d не существует", id),
                    "DeleteCompilation"
            );
        }

        compilationRepository.deleteById(id);
        log.info("DeleteCompilation. Удалена подборка с id {}", id);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(long compId, long eventId) {
        Event event = eventService.getById(eventId).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", eventId),
                "DeleteEventFromCompilation"
        ));
        Compilation compilation = getById(compId);
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
        log.info("DeleteEventFromCompilation. Удалено событие с id {} из подборки с id {}", eventId, compId);
    }

    @Override
    @Transactional
    public void addEventInCompilation(long compId, long eventId) {
        Event event = eventService.getById(eventId).orElseThrow(() -> new ObjectNotFountException(
                String.format("Событие с id %d не существует", eventId),
                "AddEventFromCompilation"
        ));
        Compilation compilation = getById(compId);
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
        log.info("AddEventFromCompilation. Добавлено событие с id {} из подборки с id {}", eventId, compId);
    }

    @Override
    @Transactional
    public void unpinCompilation(long id) {
        Compilation compilation = getById(id);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.info("UnpinCompilation. Откреплена с главной страницы подборка с id {}", id);
    }

    @Override
    @Transactional
    public void pinCompilation(long id) {
        Compilation compilation = getById(id);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.info("PinCompilation. Закреплена на главной страницы подборка с id {}", id);
    }

    @Override
    public Collection<Compilation> getByPinned(boolean pinned, int from, int size) {
        Pageable page = getPage(from, size, "id", Sort.Direction.ASC);
        return compilationRepository.findAllByPinned(pinned, page);
    }

    @Override
    public Compilation getById(long id) {
        return compilationRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Подборки с id %d не существует", id),
                "GetCompilationById"
        ));
    }

    @Override
    public boolean checkExistsById(long id) {
        return compilationRepository.existsById(id);
    }
}
