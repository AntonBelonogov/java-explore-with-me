package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewStatsDto> findAll(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean unique);

    EndpointHitDto add(EndpointHitDto endpointHitDto);
}
