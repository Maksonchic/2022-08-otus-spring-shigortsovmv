package ru.otus.books.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.BookRepositoryJpa;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import(BookRepositoryJpa.class)
class TestBookDao {

	@Autowired
	private BookRepositoryJpa bookJpa;

	@Test
	@DisplayName("Тест селекта второй книги")
	void compareFirst() {
		Book bNew1 = createDataFirstBook();
		val ref = new Object() { Book bDb1; };
		assertDoesNotThrow(() -> ref.bDb1 = bookJpa.findById(2).orElseThrow());
		assertEquals(bNew1, ref.bDb1);
	}

	@Test
	@DisplayName("Изменение книги")
	void updateBook() {
		String newTitle = "new title";
		Book bNew = createDataFirstBook();
		bNew.setTitle(newTitle);
		Book savedBook = bookJpa.save(bNew);
		assertEquals(newTitle, savedBook.getTitle());
		assertEquals(newTitle, bookJpa.findById(1).orElseThrow().getTitle());
	}

	@Test
	@DisplayName("Удаление книги")
	void removeBook() {
		Book b = bookJpa.findById(1).orElseThrow();
		bookJpa.remove(b);
		assertThrows(NoSuchElementException.class, () -> bookJpa.findById(1).orElseThrow());
	}

	private Book createDataFirstBook() {
		Author aNew = createDataFirstAuthor();
		Genre gNew = createDataFirstGenre();
		return new Book(1, "About us", 322, aNew, gNew, new ArrayList<>());
	}

	private Author createDataFirstAuthor() {
		return new Author(
				1, "Michael", "Last", "Firstov", "Middleich");
	}

	private Genre createDataFirstGenre() {
		return new Genre(1, "Horror");
	}
}
