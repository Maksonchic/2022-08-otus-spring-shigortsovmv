package ru.otus.books.repositories;

import ru.otus.books.models.Author;

public interface AuthorRepository {
    Author findById(long id);
    Author save(final Author author);
    Author findByNickName(final String nickName);
    void remove(final Author author);
}
