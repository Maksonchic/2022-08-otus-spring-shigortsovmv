package ru.otus.books.dao;

import ru.otus.books.domain.Author;
import ru.otus.books.domain.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAll();
    int count();
    void insert(final Book book);
    List<Book> getByAuthor(final Author author);
    Book getById(long id);
    void remove(final Book book);
    void update(final Book book);
    void removeByAuthor(final Author author);
}
