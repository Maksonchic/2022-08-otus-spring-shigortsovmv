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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Работаем с авторами")
@Import({AuthorDtoServiceImpl.class,AuthorRepositoryJpa.class})
class TestAuthorService {

	@Autowired
	private AuthorDtoServiceImpl authorDtoService;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {
		Author author = em.find(Author.class, 1L);
		AuthorDto authorDto = AuthorDto.createDto(author);
		assertEquals(author.getId(), authorDto.getId());
		assertEquals(author.getNickName(), authorDto.getNickName());
		assertEquals(author.getLastName(), authorDto.getLastName());
		assertEquals(author.getFirstName(), authorDto.getFirstName());
		assertEquals(author.getMiddleName(), authorDto.getMiddleName());
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
		AuthorDto a1 = AuthorDto.createDto(em.find(Author.class, 1L));
		authorDtoService.add("me", "w", "d", "s");
		assertNotEquals(a1, authorDtoService.getByNickName("me"));
		assertEquals(a1, authorDtoService.getByNickName("Michael"));
		assertEquals(a1, authorDtoService.getByNickName("MiChaEL"));
	}
}
