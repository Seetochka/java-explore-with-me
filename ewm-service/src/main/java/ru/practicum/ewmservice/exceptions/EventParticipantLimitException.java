package ru.practicum.ewmservice.exceptions;

/**
 * Исключение превышения лимита участников события
 */
public class EventParticipantLimitException extends LoggingException {
    public EventParticipantLimitException(String message, String className) {
        super(message, className);
    }
}
