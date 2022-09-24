package ru.practicum.statsservice.services;

import ru.practicum.statsservice.models.Statistic;
import ru.practicum.statsservice.dto.ViewStats;

import java.util.Collection;

public interface StatisticService {
    void createStat(Statistic statistic);

    Collection<ViewStats> getByParams(String start, String end, Collection<String> uris, boolean unique);
}
