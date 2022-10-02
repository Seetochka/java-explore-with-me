package ru.practicum.ewmservice.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmservice.exception.ObjectNotFountException;
import ru.practicum.ewmservice.exception.UserHaveNoRightsException;
import ru.practicum.ewmservice.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Обработчик ошибок
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    public ApiError handleValidationException(final ValidationException e) {
        log.error("{}. {}", e.getMethod(), e.getMessage());

        return new ApiError(
                List.of(),
                e.getMessage(),
                "Ошибка валидации",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    public ApiError handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error(e.getMessage());

        return new ApiError(
                List.of(),
                e.getMessage(),
                "Переданы невалидные агрументы",
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    public ApiError handleModelNotFoundException(final ObjectNotFountException e) {
        log.error("{}. {}", e.getMethod(), e.getMessage());

        return new ApiError(
                List.of(),
                e.getMessage(),
                "Объект не найден",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    public ApiError handleUserHaveNoRightsException(final UserHaveNoRightsException e) {
        log.error("{}. {}", e.getMethod(), e.getMessage());

        return new ApiError(
                List.of(),
                e.getMessage(),
                "Пользователь не имеет прав на данное действие",
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    public ApiError handleThrowable(final Throwable e) {
        log.error(e.getMessage());

        return new ApiError(
                List.of(),
                e.getMessage(),
                "",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }
}
