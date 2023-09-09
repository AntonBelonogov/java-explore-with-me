package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.model.Comment;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.user.mapper.UserMapper;

@Mapper(componentModel = "spring",
        uses = {UserMapper.class, EventMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "event.id", target = "eventId")
    CommentDto commentToCommentDto(Comment comment);
}
