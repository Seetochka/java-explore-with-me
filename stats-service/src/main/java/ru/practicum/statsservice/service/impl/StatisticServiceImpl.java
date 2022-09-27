package ru.practicum.statsservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsservice.model.Statistic;
import ru.practicum.statsservice.dto.ViewStats;
import ru.practicum.statsservice.repository.StatisticRepository;
import ru.practicum.statsservice.service.StatisticService;
import ru.practicum.statsservice.trait.DateTimeConverterTrait;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Сервис статистики
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService, DateTimeConverterTrait {
    private static final String APP = "ewm-service";

    private final StatisticRepository statisticRepository;

    @Override
    @Transactional
    public void createStat(Statistic statistic) {
        statistic = statisticRepository.save(statistic);

        log.info("CreateStat. Создана запись статистики с id {}", statistic.getId());
    }

    @Override
    public Collection<ViewStats> getByParams(String start, String end,
                                             Collection<String> uris, boolean unique) {
        LocalDateTime startDate = convertStringToLocalDateTime(start, null);
        LocalDateTime endDate = convertStringToLocalDateTime(end, null);

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
