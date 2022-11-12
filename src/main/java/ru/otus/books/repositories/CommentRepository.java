package ru.otus.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.books.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(final Comment comment);
    Comment findById(final long id);
}
