package ru.practicum.ewm.comment.mapper;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.model.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        if (comment == null) return null;
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getEvent().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getName(),
                comment.getStatus(),
                comment.getCreated(),
                comment.getEdited()
        );
    }
}