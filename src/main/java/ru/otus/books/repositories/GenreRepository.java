package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.books.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, Long> {
    Genre findByGenreIgnoreCase(final String genre);
}
