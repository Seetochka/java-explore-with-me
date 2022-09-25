package ru.practicum.ewmservice.exceptions;

/**
 * Исключение валидации
 */
public class ValidationException extends BaseException {
    public ValidationException(String message, String method) {
        super(message, method);
    }
}
