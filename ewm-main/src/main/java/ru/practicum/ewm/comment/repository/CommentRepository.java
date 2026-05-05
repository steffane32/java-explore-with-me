package ru.practicum.ewm.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.comment.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByEventIdAndStatus(Long eventId, Comment.CommentStatus status, Pageable pageable);

    List<Comment> findByAuthorId(Long authorId);

    List<Comment> findByStatus(Comment.CommentStatus status, Pageable pageable);

    Optional<Comment> findByIdAndAuthorId(Long id, Long authorId);
}