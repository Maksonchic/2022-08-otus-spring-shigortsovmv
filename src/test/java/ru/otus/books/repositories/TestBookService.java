package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.BookDtoServiceImpl;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import(BookDtoServiceImpl.class)
class TestBookService {

	@Autowired
	private BookDtoService bookDtoService;

	@Test
	public void checkFind() {

	}

	@Test
	@DisplayName("Тест сохранения одной книжки")
	public void testSave() {

	}

	@Test
	@DisplayName("Тест поиска по айдишнику")
	public void testFind() {

	}
}
