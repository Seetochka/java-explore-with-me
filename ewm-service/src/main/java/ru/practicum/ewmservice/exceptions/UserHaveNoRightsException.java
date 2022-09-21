package ru.practicum.ewmservice.exceptions;

/**
 * Исключение отсутствия прав
 */
public class UserHaveNoRightsException extends LoggingException {
    public UserHaveNoRightsException(String message, String className) {
        super(message, className);
    }
}
