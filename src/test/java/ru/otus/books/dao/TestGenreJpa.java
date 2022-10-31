package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.GenreRepositoryJpa;

@DataJpaTest
@DisplayName("Работаем с жанрами")
@Import(GenreRepositoryJpa.class)
class TestGenreDao {

	@Autowired
	private GenreRepositoryJpa repo;

	@Test
	@DisplayName("Сравниваем различные способы получения первого жанра")
	void compareFirst() {

	}

	@Test
	@DisplayName("Вставка нового жанра")
	void insertNewOne() {
		repo.save(new Genre(2, "Qqq"));
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
