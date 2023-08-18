package ru.practicum.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ViewStatsDto;
import ru.practicum.model.ViewStats;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ViewStatsMapper {
    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return ViewStatsDto.builder()
                .app(viewStats.getApp())
                .hits(viewStats.getHits())
                .uri(viewStats.getUri())
                .build();
    }

    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        return ViewStats.builder()
                .app(viewStatsDto.getApp())
                .hits(viewStatsDto.getHits())
                .uri(viewStatsDto.getUri())
                .build();
    }

    public static List<ViewStatsDto> toViewStatsDtoList(List<ViewStats> viewStatsList) {
        List<ViewStatsDto> viewStatsDtoList = new ArrayList<>();
        viewStatsList.forEach(vs -> viewStatsDtoList.add(toViewStatsDto(vs)));
        return viewStatsDtoList;
    }
}
