package ru.otus.books.dao;

import ru.otus.books.domain.Author;

public interface AuthorDao {
    Author getById(long id);
    Author getByNickName(final String nickName);
    void update(final Author author);
    void remove(final Author author);
    void insert(final Author author);
}
