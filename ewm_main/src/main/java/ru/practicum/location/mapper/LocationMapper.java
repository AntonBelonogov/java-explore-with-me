package ru.practicum.location.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.location.model.Location;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LocationMapper {
    Location locationDtoToLocation(LocationDto locationDto);

    LocationDto locationToLocationDto(Location location);
}
