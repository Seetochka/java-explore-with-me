package ru.practicum.statsservice.service;

import ru.practicum.statsservice.model.Statistic;
import ru.practicum.statsservice.dto.ViewStats;

import java.util.Collection;

public interface StatisticService {
    void createStat(Statistic statistic);

    Collection<ViewStats> getByParams(String start, String end, Collection<String> uris, boolean unique);
}
