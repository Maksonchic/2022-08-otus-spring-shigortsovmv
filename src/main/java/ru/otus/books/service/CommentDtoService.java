package ru.otus.books.service;

public interface CommentDtoService {
    void edit(long commentId, String newCommentText);
    void removeComment(long commentId);
}
