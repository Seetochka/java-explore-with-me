package ru.practicum.statsservice.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Обработчик ошибок
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ApiError handleThrowable(final Throwable e) {
        log.error(e.toString());
        log.error(Arrays.toString(e.getStackTrace()));
        return new ApiError(
                List.of(),
                e.getMessage(),
                "",
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }

}
