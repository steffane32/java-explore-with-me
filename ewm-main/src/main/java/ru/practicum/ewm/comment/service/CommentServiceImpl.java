package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comment.dto.*;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("Cannot comment on unpublished event");
        }

        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setEvent(event);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        comment.setStatus(Comment.CommentStatus.PENDING);

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateDto) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        if (comment.getStatus() != Comment.CommentStatus.PENDING) {
            throw new ConflictException("Can only edit pending comments");
        }

        comment.setText(updateDto.getText());
        comment.setEdited(LocalDateTime.now());

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getUserComments(Long userId) {
        return commentRepository.findByAuthorId(userId).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getEventComments(Long eventId, int from, int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return commentRepository.findByEventIdAndStatus(eventId, Comment.CommentStatus.APPROVED, pageable)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto updateCommentStatus(Long commentId, CommentStatusUpdateDto statusUpdateDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment not found"));

        Comment.CommentStatus newStatus = Comment.CommentStatus.valueOf(statusUpdateDto.getStatus().toUpperCase());
        comment.setStatus(newStatus);

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getPendingComments(int from, int size) {
        PageRequest pageable = PageRequest.of(from / size, size);
        return commentRepository.findByStatus(Comment.CommentStatus.PENDING, pageable)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }
}