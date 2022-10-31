package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.GenreRepositoryJpa;

import javax.persistence.PersistenceContext;

@DataJpaTest
@DisplayName("Работаем с жанрами")
@Import(GenreRepositoryJpa.class)
class TestGenreJpa {

	@Autowired
	private GenreRepositoryJpa repo;

	@Test
	@DisplayName("Сравниваем различные способы получения первого жанра")
	void compareFirst() {

	}

	@Test
	@DisplayName("Вставка нового жанра")
	void insertNewOne() {
		repo.save(new Genre(1, "Qqq"));
		System.out.println(repo.findByGenre("Qqq"));
	}

	@Test
	@DisplayName("Обновление первого жанра")
	void updateFirst() {

	}

	@Test
	@DisplayName("Удаление первой книги")
	void deleteFirst() {

	}
}
