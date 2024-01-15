package ru.practicum.ewmservice.exception;

/**
 * Исключение отсутствия прав
 */
public class UserHaveNoRightsException extends BaseException {
    public UserHaveNoRightsException(String message, String method) {
        super(message, method);
    }
}
