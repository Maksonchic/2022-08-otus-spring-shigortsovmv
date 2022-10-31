package ru.otus.books.repositories;

import ru.otus.books.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(final Book book);
    List<Book> findAll();
    Optional<Book> findById(final long bookId);
    void remove(final Book book);
}
