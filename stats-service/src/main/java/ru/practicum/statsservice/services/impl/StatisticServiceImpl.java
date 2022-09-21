package ru.practicum.statsservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statsservice.models.Statistic;
import ru.practicum.statsservice.dto.ViewStats;
import ru.practicum.statsservice.repositories.StatisticRepository;
import ru.practicum.statsservice.services.StatisticService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Сервис статистики
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private static final String APP = "ewm-service";

    private final StatisticRepository statisticRepository;

    @Override
    public void createStat(Statistic statistic) {
        statistic = statisticRepository.save(statistic);

        log.info("CreateStat. Создана запись статистики с id {}", statistic.getId());
    }

    @Override
    public Collection<ViewStats> getByParams(String start, String end,
                                             Collection<String> uris, boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Collection<ViewStats> viewStats;

        if (uris != null && !uris.isEmpty()) {
            if (unique) {
                viewStats = statisticRepository.getDistinctStatisticByDatesAndUris(startDate, endDate, APP, uris);
            } else {
                viewStats = statisticRepository.getStatisticByDatesAndUris(startDate, endDate, APP, uris);
            }
        } else {
            if (unique) {
                viewStats = statisticRepository.getDistinctStatisticByDates(startDate, endDate, APP);
            } else {
                viewStats = statisticRepository.getStatisticByDates(startDate, endDate, APP);
            }
        }

        return viewStats;
    }
}
