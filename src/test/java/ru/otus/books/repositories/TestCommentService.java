package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.service.CommentDtoService;
import ru.otus.books.service.CommentDtoServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Import(CommentDtoServiceImpl.class)
@DisplayName("Работаем с комментами")
class TestCommentService {

	@Autowired
	private CommentDtoService commentDtoService;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {
		Book book = em.find(Book.class, 2L);
		Comment first = book.getComments().get(0);
		CommentDto commentDto = new CommentDto(1L, "So bad, I should have bought a winrar");
		assertEquals(commentDto, CommentDto.createDto(first));
	}
}
