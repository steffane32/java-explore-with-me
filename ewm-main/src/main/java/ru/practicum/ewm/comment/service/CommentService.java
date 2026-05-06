package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.*;
import java.util.List;

public interface CommentService {

    CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentDto> getUserComments(Long userId);

    List<CommentDto> getEventComments(Long eventId, int from, int size);

    void deleteCommentByAdmin(Long commentId);

    CommentDto updateCommentStatus(Long commentId, CommentStatusUpdateDto statusUpdateDto);

    List<CommentDto> getPendingComments(int from, int size);
}