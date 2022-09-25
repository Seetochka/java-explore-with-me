package ru.practicum.ewmservice.exceptions;

/**
 * Исключение некорректного state у события
 */
public class EventStateException extends BaseException {
    public EventStateException(String message, String method) {
        super(message, method);
    }
}
