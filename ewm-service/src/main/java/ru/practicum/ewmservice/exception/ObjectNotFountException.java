package ru.practicum.ewmservice.exception;

/**
 * Исключение отсутствия объекта
 */
public class ObjectNotFountException extends BaseException {
    public ObjectNotFountException(String message, String method) {
        super(message, method);
    }
}
