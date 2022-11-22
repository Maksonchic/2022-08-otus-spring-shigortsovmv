package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.books.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, Long> {
    Book findByComments(final long commentId);
}
