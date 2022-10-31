package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@DisplayName("Работаем с жанрами")
@Import(GenreRepositoryJpa.class)
class TestGenreRepositoryJpa {

	@Autowired
	private GenreRepositoryJpa repo;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Сравниваем различные способы получения первого жанра")
	void compareFirst() {
		assertNotNull(repo.findByGenre("Horror"));
		assertNotNull(repo.findByGenre("hoRRoR"));
		assertEquals(em.find(Genre.class, 1L), repo.findByGenre("Horror"));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка нового жанра")
	void insertNewOne() {
		repo.save(new Genre(0, "Qqq"));
		assertEquals(em.find(Genre.class, 2L).getGenre(), "Qqq");
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Обновление первого жанра")
	void updateFirst() {
		repo.save(new Genre(1, "Qqq"));
		assertEquals(em.find(Genre.class, 1L).getGenre(), "Qqq");
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление первой книги")
	void deleteFirst() {
		Genre g = em.find(Genre.class, 1L);
		repo.remove(g);
		assertNull(em.find(Genre.class, 1L));
	}
}
