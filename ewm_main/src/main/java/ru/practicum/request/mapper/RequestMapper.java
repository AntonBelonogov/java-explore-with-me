package ru.practicum.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.model.Request;
import ru.practicum.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CategoryMapper.class, EventMapper.class, LocationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RequestMapper {
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "event.id", target = "event")
    RequestDto requestToRequestDto(Request request);
}
