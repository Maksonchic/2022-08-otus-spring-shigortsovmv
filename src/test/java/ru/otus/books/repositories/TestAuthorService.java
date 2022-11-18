package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.models.Author;
import ru.otus.books.service.AuthorDtoService;
import ru.otus.books.service.AuthorDtoServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Работаем с авторами")
@Import(AuthorDtoServiceImpl.class)
class TestAuthorService {

	@Autowired
	private AuthorDtoService authorDtoService;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {

	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertDtoEntity() {
		AuthorDto authorDto =
				AuthorDto.createDto(
						new Author(1L, "Michael", "Last", "Firstov", "Middleich"));
		Author author = AuthorDto.createEntity(authorDto);
		assertEquals(author.getId(), authorDto.getId());
		assertEquals(author.getNickName(), authorDto.getNickName());
		assertEquals(author.getLastName(), authorDto.getLastName());
		assertEquals(author.getFirstName(), authorDto.getFirstName());
		assertEquals(author.getMiddleName(), authorDto.getMiddleName());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Сравниваем различные способы получения первого автора")
	void compareFirst() {

	}
}
