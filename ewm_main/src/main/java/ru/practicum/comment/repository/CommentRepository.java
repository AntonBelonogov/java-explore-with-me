package ru.practicum.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorIdOrderByCreatedOnAsc(Long userId, Pageable pageable);

    List<Comment> findAllByEventIdOrderByCreatedOnAsc(Long eventId, Pageable pageable);
}
