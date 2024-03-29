package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class EwmStatsClient {
    private final WebClient client;

    private static final String HOST = "http://stats-server:9090";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public EwmStatsClient() {
        this.client = WebClient.create(HOST);
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean unique) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(formatter))
                        .queryParam("end", end.format(formatter))
                        .queryParam("uris", uriList)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToFlux(ViewStatsDto.class)
                .collectList()
                .block();
    }

    public void hitRequest(EndpointHitDto endpointHitDto) {
        client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(endpointHitDto)
                .retrieve()
                .bodyToMono(EndpointHitDto.class)
                .block();
    }
}

