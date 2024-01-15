package ru.practicum.ewmservice.exception;

/**
 * Базовое исключение
 */
public abstract class BaseException extends RuntimeException {
    private final String method;

    public BaseException(String message, String method) {
        super(message);

        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
