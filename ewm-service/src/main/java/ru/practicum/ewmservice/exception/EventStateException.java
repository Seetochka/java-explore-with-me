package ru.practicum.ewmservice.exception;

/**
 * Исключение некорректного state у события
 */
public class EventStateException extends BaseException {
    public EventStateException(String message, String method) {
        super(message, method);
    }
}
