package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.CommentInput;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.AccessException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto save(Long userId, Long eventId, CommentInput commentInput) {
        Comment comment = Comment.builder()
                .text(commentInput.getText())
                .author(findUserById(userId))
                .event(findEventById(eventId))
                .createdOn(LocalDateTime.now())
                .build();

        return commentMapper.commentToCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto update(Long userId, Long commentId, CommentInput commentInput) {
        checkUserById(userId);
        Comment commentForUpdate = findById(commentId);
        if (!Objects.equals(commentForUpdate.getAuthor().getId(), userId)) {
            throw new AccessException("Access exception");
        }

        commentForUpdate.setText(commentInput.getText());
        return commentMapper.commentToCommentDto(commentRepository.save(commentForUpdate));
    }

    @Override
    public List<CommentDto> findByAuthorId(Long userId, Pageable pageable) {
        checkUserById(userId);
        return commentRepository.findAllByAuthorIdOrderByCreatedOnAsc(userId, pageable).stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId, Long commentId) {
        checkUserById(userId);
        Comment commentForDelete = findById(commentId);
        if (!Objects.equals(commentForDelete.getAuthor().getId(), userId)) {
            throw new AccessException("Access exception");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findByEventId(Long eventId, Pageable pageable) {
        checkEventById(eventId);
        return commentRepository.findAllByEventIdOrderByCreatedOnAsc(eventId, pageable).stream()
                .map(commentMapper::commentToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteForAdmin(Long commentId) {
        checkCommentById(commentId);
        commentRepository.deleteById(commentId);
    }

    private Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> {
            throw new NotFoundException("Commentary was not found");
        });
    }

    private Event findEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            throw new NotFoundException("Commentary was not found");
        });
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundException("Commentary was not found");
        });
    }

    private void checkUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Commentary was not found");
        }
    }

    private void checkEventById(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Commentary was not found");
        }
    }

    private void checkCommentById(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Commentary was not found");
        }
    }
}
