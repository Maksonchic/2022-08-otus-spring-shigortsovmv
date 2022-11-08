package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.models.Genre;
import ru.otus.books.service.GenreDtoService;
import ru.otus.books.service.GenreDtoServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@DisplayName("Работаем с жанрами")
@Import({GenreDtoServiceImpl.class,GenreRepositoryJpa.class})
class TestGenreService {

	@Autowired
	private GenreDtoService genreDtoService;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {
		Genre genre = em.find(Genre.class, 1L);
		GenreDto genreDto = genreDtoService.getByGenre("horror");
		assertEquals(genre.getId(), genreDto.getId());
		assertEquals(genre.getGenre(), genreDto.getGenre());
	}
}
