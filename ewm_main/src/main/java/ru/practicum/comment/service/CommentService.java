package ru.practicum.comment.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentInput;

import java.util.List;

public interface CommentService {

    CommentDto save(Long userId, Long eventId, CommentInput commentInput);

    CommentDto update(Long userId, Long commentId, CommentInput commentInput);

    List<CommentDto> findByAuthorId(Long userId, Pageable pageable);

    void delete(Long userId, Long commentId);

    List<CommentDto> findByEventId(Long eventId, Pageable pageable);

    void deleteForAdmin(Long commentId);
}
