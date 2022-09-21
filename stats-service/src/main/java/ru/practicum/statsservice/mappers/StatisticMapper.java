package ru.practicum.statsservice.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.statsservice.dto.EndpointHitDto;
import ru.practicum.statsservice.models.Statistic;

/**
 * Маппер статистики
 */
@Component
public class StatisticMapper {
    public Statistic toStat(EndpointHitDto endpointHit) {
        return new Statistic(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }
}
