package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.exception.InvalidDataException;
import ru.practicum.mapper.EndpointHitMapper;
import ru.practicum.mapper.ViewStatsMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public List<ViewStatsDto> findAll(LocalDateTime start, LocalDateTime end, List<String> uriList, Boolean unique) {
        if (start.isAfter(end)) {
            throw new InvalidDataException("Incorrect date params");
        }
        if (unique) {
            return ViewStatsMapper.toViewStatsDtoList(statsRepository.findUniqueViewStats(start, end, uriList));
        } else {
            return ViewStatsMapper.toViewStatsDtoList(statsRepository.findAllViewStats(start, end, uriList));
        }
    }

    @Override
    @Transactional
    public EndpointHitDto add(EndpointHitDto endpointHitDto) {
        statsRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        return endpointHitDto;
    }
}
