package ru.otus.books.dao;

import ru.otus.books.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getById(long id);
    List<Author> getAll();
    Author getByNickName(final String nickName);
    void update(final Author author);
    void remove(final Author author);
    void insert(final Author author);
}
