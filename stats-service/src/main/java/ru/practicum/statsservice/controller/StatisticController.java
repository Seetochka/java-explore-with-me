package ru.practicum.statsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statsservice.dto.EndpointHitDto;
import ru.practicum.statsservice.dto.ViewStats;
import ru.practicum.statsservice.mapper.StatisticMapper;
import ru.practicum.statsservice.service.StatisticService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Контроллер отвечающий за действия со статистикой
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    private final StatisticMapper statisticMapper;

    @PostMapping("/hit")
    public HttpStatus createStat(@Valid @RequestBody EndpointHitDto endpointHit) {
        statisticService.createStat(statisticMapper.toStat(endpointHit));
        return HttpStatus.OK;
    }

    @GetMapping("/stats")
    public Collection<ViewStats> getByParams(@RequestParam String start,
                                             @RequestParam String end,
                                             @RequestParam(required = false) Collection<String> uris,
                                             @RequestParam(required = false, defaultValue = "false") boolean unique) {
        return statisticService.getByParams(start, end, uris, unique);
    }
}
