package ru.practicum.ewmservice.exception;

/**
 * Исключение превышения лимита участников события
 */
public class EventParticipantLimitException extends BaseException {
    public EventParticipantLimitException(String message, String method) {
        super(message, method);
    }
}
