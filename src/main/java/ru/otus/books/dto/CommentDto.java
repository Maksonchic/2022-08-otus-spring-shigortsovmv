package ru.otus.books.dto;

import ru.otus.books.models.Comment;

public class CommentDto {
    private final long id;
    private final String message;

    public CommentDto(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
    }

    public static CommentDto createDto(Comment comment) {
        return new CommentDto(comment);
    }

    public static Comment createEntity(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getMessage());
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"message\":\"" + message + "\"" +
                '}';
    }
}
