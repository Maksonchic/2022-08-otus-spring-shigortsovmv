package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Работаем с жанрами")
@Import(GenreDaoJdbc.class)
class TestGenreDao {

	@Autowired
	private GenreDao genreDao;

	@Test
	@DisplayName("Сравниваем различные способы получения первого жанра")
	void compareFirst() {
		Genre g1 = genreDao.getById(1);
		Genre g2 = genreDao.getByGenre("Horror");
		assertEquals(g1, g2);
	}

	@Test
	@DisplayName("Вставка нового жанра")
	void insertNewOne() {
		List<Genre> all = genreDao.getAll();
		Genre gNew = new Genre(2, "Test genre");
		all.add(gNew);
		genreDao.insert(gNew);
		assertEquals(all, genreDao.getAll());
	}

	@Test
	@DisplayName("Обновление первого жанра")
	void updateFirst() {
		Genre genre = new Genre(1, "Test genre");
		assertNotEquals(genre, genreDao.getById(1));
		int genresCountBefore = genreDao.getAll().size();
		genreDao.update(genre);
		Genre bookUpdated = genreDao.getById(1);
		assertEquals(genre, bookUpdated);
		assertEquals(genreDao.getAll().size(), genresCountBefore);
	}

	@Test
	@DisplayName("Удаление первой книги")
	void deleteFirst() {
		Genre genre = genreDao.getById(1);
		assertNotNull(genre);
		int genresCountBefore = genreDao.getAll().size();
		genreDao.remove(genre);
		assertEquals(genreDao.getAll().size(), genresCountBefore - 1);
	}

	@Test
	@DisplayName("Удаление несуществующей книги по прикладным данным")
	void dirtyDelete() {
		Genre genre = new Genre(1, "Test genre");
		int genresCountBefore = genreDao.getAll().size();
		genreDao.remove(genre);
		assertEquals(genreDao.getAll().size(), genresCountBefore);
	}
}
