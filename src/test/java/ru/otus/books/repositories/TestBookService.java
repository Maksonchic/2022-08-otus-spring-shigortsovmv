package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Book;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.BookDtoServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import(BookDtoServiceImpl.class)
class TestBookService {

	@Autowired
	private BookDtoService bookDtoService;

	@PersistenceContext
	private EntityManager em;

	@Test
	public void checkFind() {
		assertEquals(BookDto.createDto(em.find(Book.class, 1L)), bookDtoService.getById(1));
	}

	@Test
	@DisplayName("Тест сохранения одной книжки")
	public void testSave() {
		BookDto add = bookDtoService.add("new Book", 123, "michael", "horror");
		BookDto dto = BookDto.createDto(em.find(Book.class, 3L), true);
		assertEquals(dto, add);
	}

	@Test
	@DisplayName("Тест поиска по айдишнику")
	public void testFind() {
		assertEquals(BookDto.createDto(em.find(Book.class, 1L)), bookDtoService.getById(1));
	}
}
