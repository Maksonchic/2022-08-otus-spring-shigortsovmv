package ru.otus.books.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.BookRepositoryJpa;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.books.dao.utils.DataCreator.createDataFirstBook;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import(BookRepositoryJpa.class)
class TestBookJpa {

	@Autowired
	private BookRepositoryJpa repo;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставляем книгу с комментами")
	void insertNewBook() {
		assertThrows(NoSuchElementException.class, () -> repo.findById(1).orElseThrow());
		Book bNew3 = createDataFirstBook();
		bNew3.getComments().add(new Comment(0, "a"));
		bNew3.getComments().add(new Comment(0, "b"));
		bNew3.getComments().add(new Comment(0, "d"));
		repo.save(bNew3);
		System.out.println(repo.findById(1));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Изменение книги")
	void updateBook() {
		Book bNew = createDataFirstBook();
		String newTitle = "new title + " + bNew.getTitle();
		repo.save(bNew);
		assertInstanceOf(Book.class, repo.findById(bNew.getId()).orElseThrow());
		bNew.setTitle(newTitle);
		Book savedBook = repo.save(bNew);
		assertEquals(newTitle, savedBook.getTitle());
		assertEquals(newTitle, repo.findById(1).orElseThrow().getTitle());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление книги")
	void removeBook() {
		Book bNew = createDataFirstBook();
		repo.save(bNew);
		assertInstanceOf(Book.class, repo.findById(bNew.getId()).orElseThrow());
		repo.remove(repo.findById(1).orElseThrow());
		assertThrows(NoSuchElementException.class, () -> repo.findById(1).orElseThrow());
	}
}
