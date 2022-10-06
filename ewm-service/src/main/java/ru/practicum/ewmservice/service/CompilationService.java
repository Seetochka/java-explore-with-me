package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.model.Compilation;

import java.util.Collection;

public interface CompilationService {
    Compilation create(Compilation compilation);

    void delete(long id);

    void deleteEventFromCompilation(long compId, long eventId);

    void addEventInCompilation(long compId, long eventId);

    void unpinCompilation(long id);

    void pinCompilation(long id);

    Collection<Compilation> getByPinned(boolean pinned, int from, int size);

    Compilation getById(long id);

    boolean checkExistsById(long id);
}
