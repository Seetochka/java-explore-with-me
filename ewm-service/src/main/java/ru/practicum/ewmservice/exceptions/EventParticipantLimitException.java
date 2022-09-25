package ru.practicum.ewmservice.exceptions;

/**
 * Исключение превышения лимита участников события
 */
public class EventParticipantLimitException extends BaseException {
    public EventParticipantLimitException(String message, String method) {
        super(message, method);
    }
}
