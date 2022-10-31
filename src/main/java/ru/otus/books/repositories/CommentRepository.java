package ru.otus.books.repositories;

import ru.otus.books.models.Comment;

import java.util.Optional;

public interface CommentRepository {
    Comment save(final Comment comment);
    Optional<Comment> findById(long id);
    void update(final Comment comment);
    void remove(final Comment comment);
}
