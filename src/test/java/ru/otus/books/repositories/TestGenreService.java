package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.service.GenreDtoService;
import ru.otus.books.service.GenreDtoServiceImpl;

@DataJpaTest
@Import(GenreDtoServiceImpl.class)
@DisplayName("Работаем с жанрами")
class TestGenreService {

	@Autowired
	private GenreDtoService genreDtoService;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {

	}
}
