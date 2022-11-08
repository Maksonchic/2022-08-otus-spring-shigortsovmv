package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.BookDtoServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import({BookDtoServiceImpl.class,BookRepositoryJpa.class,AuthorRepositoryJpa.class,GenreRepositoryJpa.class})
class TestBookService {

	@Autowired
	private BookDtoService bookDtoService;

	@PersistenceContext
	private EntityManager em;
}
