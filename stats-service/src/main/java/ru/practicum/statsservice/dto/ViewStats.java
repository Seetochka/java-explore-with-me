package ru.practicum.statsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс просмотров статистики
 */
@Data
@AllArgsConstructor
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
