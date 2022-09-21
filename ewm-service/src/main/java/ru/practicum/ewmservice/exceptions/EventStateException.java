package ru.practicum.ewmservice.exceptions;

/**
 * Исключение некорректного state у события
 */
public class EventStateException extends LoggingException {
    public EventStateException(String message, String className) {
        super(message, className);
    }
}
