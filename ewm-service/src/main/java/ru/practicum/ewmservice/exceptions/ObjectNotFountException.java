package ru.practicum.ewmservice.exceptions;

/**
 * Исключение отсутствия объекта
 */
public class ObjectNotFountException extends LoggingException {
    public ObjectNotFountException(String message, String className) {
        super(message, className);
    }
}
