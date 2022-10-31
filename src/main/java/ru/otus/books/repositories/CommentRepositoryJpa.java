package ru.otus.books.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.books.models.Comment;

import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {
    @Override
    public Comment save(Comment comment) {
        return null;
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void update(Comment comment) {

    }

    @Override
    public void remove(Comment comment) {

    }
}
