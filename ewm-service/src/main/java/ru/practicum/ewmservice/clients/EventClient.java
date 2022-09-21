package ru.practicum.ewmservice.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmservice.dto.EndpointHitDto;
import ru.practicum.ewmservice.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventClient extends BaseClient {
    private static final String API_PREFIX = "/";

    @Autowired
    public EventClient(@Value("${stats-service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void createStat(HttpServletRequest httpRequest) {
        EndpointHitDto hitDto = new EndpointHitDto(
                null,
                "ewm-service",
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr(),
                LocalDateTime.now()
        );

        post("hit", hitDto);
    }

    public Collection<ViewStats> getStats(Collection<String> uris) {
        String start = LocalDateTime.now().minusDays(10L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        ResponseEntity<Object[]> response = get("stats?start=" + start + "&end=" + end + "&uris="
                + String.join("&uris=", uris));

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(object -> (new ObjectMapper()).convertValue(object, ViewStats.class))
                .collect(Collectors.toList());
    }
}
