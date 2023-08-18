package ru.practicum.service;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    /**
     * Метод для получения статистики
     *
     * @param start   начало периода
     * @param end     конец периода
     * @param uriList список uri, по которым нужно получить статистику
     * @param unique  true-статистика по уникальным ip, false-соответственно, нет
     * @return список экземпляров ViewStatsDto, отражающих статистику
     */
    List<ViewStatsDto> findAll(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean unique);

    /**
     * Метод для добавления статистики по запросу
     *
     * @param endpointHitDto запрос для добавления в статистику
     * @return добавленный EndpointHitDto
     */
    EndpointHitDto add(EndpointHitDto endpointHitDto);
}
