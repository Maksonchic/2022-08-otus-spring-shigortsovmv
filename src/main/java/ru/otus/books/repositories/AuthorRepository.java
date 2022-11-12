package ru.otus.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.books.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNickNameIgnoreCase(final String nickName);
}
