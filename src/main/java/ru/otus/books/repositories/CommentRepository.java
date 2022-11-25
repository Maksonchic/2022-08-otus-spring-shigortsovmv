package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.books.models.Comment;

public interface CommentRepository extends MongoRepository<Comment, Long> {
    Comment findById(final long id);
}
