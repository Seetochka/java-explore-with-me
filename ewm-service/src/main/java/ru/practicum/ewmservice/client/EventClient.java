package ru.practicum.ewmservice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewmservice.dto.EndpointHitDto;
import ru.practicum.ewmservice.dto.ViewStats;
import ru.practicum.ewmservice.trait.DateTimeConverterTrait;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventClient extends BaseClient implements DateTimeConverterTrait {
    private static final String APP = "ewm-main-service";

    protected final ObjectMapper mapper;

    public EventClient(RestTemplate rest, ObjectMapper mapper) {
        super(rest);

        this.mapper = mapper;
    }

    public void createStat(HttpServletRequest httpRequest) {
        EndpointHitDto hitDto = new EndpointHitDto(
                null,
                APP,
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr(),
                LocalDateTime.now()
        );

        post("hit", hitDto);
    }

    public Collection<ViewStats> getStats(Collection<String> uris) {
        String start = LocalDateTime.now().minusDays(10L).format(DateTimeFormatter.ofPattern(dateTimeFormat));
        String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormat));
        ResponseEntity<Object[]> response = get("stats?start=" + start + "&end=" + end + "&uris="
                + String.join("&uris=", uris));
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(object -> mapper.convertValue(object, ViewStats.class))
                .collect(Collectors.toList());
    }
}
