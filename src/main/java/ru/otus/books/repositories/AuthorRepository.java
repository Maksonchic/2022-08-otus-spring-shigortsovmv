package ru.otus.books.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.books.models.Author;

public interface AuthorRepository extends MongoRepository<Author, Long> {
    Author findByNickNameIgnoreCase(final String nickName);
}
