package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.books.models.Book;

public interface BookRepository extends MongoRepository<Book, Long> {
    Book findById(final long bookId);
    Book findByComments(final long commentId);
}
