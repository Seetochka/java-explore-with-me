package ru.practicum.ewmservice.exceptions;

import lombok.extern.slf4j.Slf4j;

/**
 * Исключение записывающее логи
 */
@Slf4j
public class LoggingException extends Exception {
    public LoggingException(String message, String className) {
        super(message);

        log.error("{}. {}", className, message);
    }
}
