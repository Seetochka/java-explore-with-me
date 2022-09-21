package ru.practicum.ewmservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.exceptions.ObjectNotFountException;
import ru.practicum.ewmservice.models.Compilation;
import ru.practicum.ewmservice.models.Event;
import ru.practicum.ewmservice.repositories.CompilationRepository;
import ru.practicum.ewmservice.services.CompilationService;
import ru.practicum.ewmservice.services.EventService;
import ru.practicum.ewmservice.traits.PageTrait;

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
    public Compilation create(Compilation compilation) throws ObjectNotFountException {
        Collection<Event> events = new ArrayList<>();

        for (Event event : compilation.getEvents()) {
            events.add(eventService.getById(event.getId()));
        }

        compilation.setEvents(events);

        compilation = compilationRepository.save(compilation);

        log.info("CreateCompilation. Создана подборка с id {}", compilation.getId());
        return compilation;
    }

    @Override
    public void delete(long id) throws ObjectNotFountException {
        if (checkExistsById(id)) {
            throw new ObjectNotFountException(
                    String.format("Подборки с id %d не существует", id),
                    "DeleteCompilation"
            );
        }

        compilationRepository.deleteById(id);

        log.info("DeleteCompilation. Удалена подборка с id {}", id);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) throws ObjectNotFountException {
        Event event = eventService.getById(eventId);
        Compilation compilation = getById(compId);

        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);

        log.info("DeleteEventFromCompilation. Удалено событие с id {} из подборки с id {}", eventId, compId);
    }

    @Override
    public void addEventInCompilation(long compId, long eventId) throws ObjectNotFountException {
        Event event = eventService.getById(eventId);
        Compilation compilation = getById(compId);

        compilation.getEvents().add(event);
        compilationRepository.save(compilation);

        log.info("AddEventFromCompilation. Добавлено событие с id {} из подборки с id {}", eventId, compId);
    }

    @Override
    public void unpinCompilation(long id) throws ObjectNotFountException {
        Compilation compilation = getById(id);

        compilation.setPinned(false);

        compilationRepository.save(compilation);

        log.info("UnpinCompilation. Откреплена с главной страницы подборка с id {}", id);
    }

    @Override
    public void pinCompilation(long id) throws ObjectNotFountException {
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
    public Compilation getById(long id) throws ObjectNotFountException {
        return compilationRepository.findById(id).orElseThrow(() -> new ObjectNotFountException(
                String.format("Подборки с id %d не существует", id),
                "GetCompilationById"
        ));
    }

    @Override
    public boolean checkExistsById(long id) {
        return !compilationRepository.existsById(id);
    }
}
