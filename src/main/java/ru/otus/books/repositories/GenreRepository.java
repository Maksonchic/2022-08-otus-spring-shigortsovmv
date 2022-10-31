package ru.otus.books.repositories;

import ru.otus.books.models.Genre;

import java.util.Optional;

public interface GenreRepository {
    Genre save(final Genre genre);
    Genre findByGenre(final String genre);
    Genre findById(final long id);
    void remove(final Genre genre);
}
