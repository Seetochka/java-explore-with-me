package ru.practicum.ewmservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewmservice.client.EventClient;

@Configuration
public class WebClientConfiguration {
    private static final String API_PREFIX = "/";

    @Value("${stats-service.url}")
    String serverUrl;

    @Bean
    public EventClient eventClient(RestTemplateBuilder builder, ObjectMapper mapper) {
        RestTemplate rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
        return new EventClient(rest, mapper);
    }
}
