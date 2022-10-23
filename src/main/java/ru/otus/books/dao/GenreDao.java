package ru.otus.books.dao;

import ru.otus.books.domain.Genre;

import java.util.List;

public interface GenreDao {
    Genre getById(long id);
    Genre getByGenre(String genre);
    List<Genre> getAll();
    void insert(final Genre genre);
    void update(final Genre genre);
    void remove(final Genre genre);
}
