package ru.practicum.statsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statsservice.dto.ViewStats;
import ru.practicum.statsservice.model.Statistic;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Интерфейс репозитория статистики
 */
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Query("select new ru.practicum.statsservice.dto.ViewStats(s.app, s.uri, count(s)) from Statistic as s " +
                    "where s.timestamp between ?1 and ?2 and s.app=?3 and s.uri in(?4) " +
                    "group by s.uri, s.app")
    List<ViewStats> getStatisticByDatesAndUris(LocalDateTime start, LocalDateTime end, String app, Collection<String> uris);

    @Query("select new ru.practicum.statsservice.dto.ViewStats(s.app, s.uri, count(distinct s.ip)) from Statistic as s " +
            "where s.timestamp between ?1 and ?2 and s.app=?3 and s.uri in(?4) " +
            "group by s.uri, s.app")
    List<ViewStats> getDistinctStatisticByDatesAndUris(LocalDateTime start, LocalDateTime end, String app, Collection<String> uris);

    @Query("select new ru.practicum.statsservice.dto.ViewStats(s.app, s.uri, count(s)) from Statistic as s " +
            "where s.timestamp between ?1 and ?2 and s.app=?3 " +
            "group by s.uri, s.app")
    List<ViewStats> getStatisticByDates(LocalDateTime start, LocalDateTime end, String app);

    @Query("select new ru.practicum.statsservice.dto.ViewStats(s.app, s.uri, count(distinct s.ip)) from Statistic as s " +
            "where s.timestamp between ?1 and ?2 and s.app=?3 " +
            "group by s.uri, s.app")
    List<ViewStats> getDistinctStatisticByDates(LocalDateTime start, LocalDateTime end, String app);
}
