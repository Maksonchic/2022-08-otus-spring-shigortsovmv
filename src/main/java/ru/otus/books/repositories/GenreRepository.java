package ru.otus.books.repositories;

import ru.otus.books.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Genre save(final Genre genre);
    Genre findByGenre(final String genre);
    List<Genre> findAll();
    Genre findById(final long id);
    void remove(final Genre genre);
}
