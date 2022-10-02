package ru.practicum.ewmservice.exception;

/**
 * Исключение записывающее логи
 */
public abstract class BaseException extends Exception {
    private final String method;

    public BaseException(String message, String method) {
        super(message);

        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
