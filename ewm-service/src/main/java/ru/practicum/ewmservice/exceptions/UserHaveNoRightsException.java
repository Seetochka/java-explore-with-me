package ru.practicum.ewmservice.exceptions;

/**
 * Исключение отсутствия прав
 */
public class UserHaveNoRightsException extends BaseException {
    public UserHaveNoRightsException(String message, String method) {
        super(message, method);
    }
}
