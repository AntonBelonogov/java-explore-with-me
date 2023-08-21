package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.dto.EventDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.Event;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {CategoryMapper.class, UserMapper.class, LocationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {
    EventDto eventToEventDto(Event event);

    EventShortDto eventToEventShortDto(Event event);

    EventFullDto eventToEventFullDto(Event event);
}
