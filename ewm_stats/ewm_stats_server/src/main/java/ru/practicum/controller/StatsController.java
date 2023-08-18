package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    public EndpointHitDto add(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Получен запрос на добавление статистики");
        return statsService.add(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> findAll(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                      @RequestParam(required = false) List<String> uris,
                                      @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на выгрузку статистики");
        return statsService.findAll(start, end, uris, unique);
    }
}
