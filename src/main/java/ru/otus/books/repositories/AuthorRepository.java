package ru.otus.books.repositories;

import ru.otus.books.models.Author;

import java.util.List;

public interface AuthorRepository {
    Author findById(long id);
    List<Author> findAll();
    Author save(final Author author);
    Author findByNickName(final String nickName);
    void remove(final Author author);
}
