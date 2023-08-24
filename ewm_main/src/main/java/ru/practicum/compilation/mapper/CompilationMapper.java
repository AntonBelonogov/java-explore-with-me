package ru.practicum.compilation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, CategoryMapper.class, EventMapper.class, LocationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompilationMapper {
    CompilationDto compilationToCompilationDto(Compilation compilation);
}
