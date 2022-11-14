package ru.otus.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.books.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    void deleteByGenreIgnoreCase(final String genre);
    Genre findByGenreIgnoreCase(final String genre);
}
