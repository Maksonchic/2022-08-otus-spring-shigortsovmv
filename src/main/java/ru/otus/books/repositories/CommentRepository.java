package ru.otus.books.repositories;

import ru.otus.books.models.Comment;

public interface CommentRepository {
    Comment save(final Comment comment);
    Comment findById(final long id);
    void remove(final Comment comment);
    void removeById(long id);
}
