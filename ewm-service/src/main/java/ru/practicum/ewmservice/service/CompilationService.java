package ru.practicum.ewmservice.service;

import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.model.Compilation;

import java.util.Collection;

public interface CompilationService {
    Compilation create(Compilation compilation) throws ObjectNotFountException;

    void delete(long id) throws ObjectNotFountException;

    void deleteEventFromCompilation(long compId, long eventId) throws ObjectNotFountException;

    void addEventInCompilation(long compId, long eventId) throws ObjectNotFountException;

    void unpinCompilation(long id) throws ObjectNotFountException;

    void pinCompilation(long id) throws ObjectNotFountException;

    Collection<Compilation> getByPinned(boolean pinned, int from, int size);

    Compilation getById(long id) throws ObjectNotFountException;

    boolean checkExistsById(long id);
}
