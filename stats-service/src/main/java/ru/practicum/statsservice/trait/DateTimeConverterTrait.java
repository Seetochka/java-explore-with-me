package ru.practicum.statsservice.trait;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Трейт конвертации строки в дату по паттерну
 */
public interface DateTimeConverterTrait {
    String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    default LocalDateTime convertStringToLocalDateTime(String dateTime, String pattern) {
        if (!StringUtils.hasText(dateTime)) {
            return null;
        }

        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(StringUtils.hasText(pattern) ? pattern
                : dateTimeFormat));
    }
}
