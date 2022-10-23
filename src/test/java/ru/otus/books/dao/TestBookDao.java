package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.domain.Author;
import ru.otus.books.domain.Book;
import ru.otus.books.domain.Genre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Работаем с книгами")
@Import(BookDaoJdbc.class)
class TestBookDao {

	@Autowired
	private BookDao bookDao;

	@Test
	@DisplayName("Сравниваем различные способы получения первой книги")
	void compareFirst() {
		Author author =
				new Author(1, "Michael", "Last", "Firstov", "Middleich");
		Book b1 = bookDao.getById(1);
		Book b2 = bookDao.getByAuthor(author).stream().filter((b) -> b.id() == 1).toList().get(0);
		assertEquals(b1, b2);
		b2 = bookDao.getAll().stream().filter((b) -> b.id() == 1).toList().get(0);
		assertEquals(b1, b2);
	}

	@Test
	@DisplayName("Вставка новой книги")
	void insertNewOne() {
		List<Book> all = bookDao.getAll();
		Book bNew = createDefaultBook(3);
		all.add(bNew);
		bookDao.insert(bNew);
		assertEquals(all, bookDao.getAll());
	}

	@Test
	@DisplayName("Обновление первой книги")
	void updateFirst() {
		Book book = createDefaultBook(1);
		assertNotEquals(book, bookDao.getById(1));
		int booksCountBefore = bookDao.count();
		bookDao.update(book);
		Book bookUpdated = bookDao.getById(1);
		assertEquals(book, bookUpdated);
		assertEquals(bookDao.count(), booksCountBefore);
	}

	@Test
	@DisplayName("Удаление первой книги")
	void deleteFirst() {
		Book book = bookDao.getById(1);
		assertNotNull(book);
		int booksCountBefore = bookDao.count();
		bookDao.remove(book);
		assertEquals(bookDao.count(), booksCountBefore - 1);
	}

	@Test
	@DisplayName("Удаление несуществующей книги по прикладным данным")
	void dirtyDelete() {
		Book book = createDefaultBook(1);
		int booksCountBefore = bookDao.count();
		bookDao.remove(book);
		assertEquals(bookDao.count(), booksCountBefore);
	}

	@Test
	@DisplayName("Вставка книги с кривыми прикладными данными")
	void dirtyInsert() {
		List<Book> all = bookDao.getAll();
		Author aNew =
				new Author(111, "www", "rrr", "sss", "vvv");
		Genre gNew = new Genre(111, "ssss");
		Book dBook = new Book(3, "New", 200, aNew, gNew);
		bookDao.insert(dBook);
		assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> bookDao.getById(3));
		aNew = createDefaultAuthor();
		gNew = new Genre(111, "ssss");
		dBook = new Book(4, "New", 200, aNew, gNew);
		bookDao.insert(dBook);
		assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> bookDao.getById(3));
		aNew = createDefaultAuthor();
		gNew = createDefaultGenre();
		dBook = new Book(5, "New", 200, aNew, gNew);
		bookDao.insert(dBook);
		assertDoesNotThrow(() -> bookDao.getById(5));
		all.add(dBook);
		assertEquals(all, bookDao.getAll());
	}

	private Book createDefaultBook(long id) {
		Author aNew = createDefaultAuthor();
		Genre gNew = createDefaultGenre();
		return new Book(id, "New", 200, aNew, gNew);
	}

	private Author createDefaultAuthor() {
		return new Author(
				1, "Michael", "Last", "Firstov", "Middleich");
	}

	private Genre createDefaultGenre() {
		return new Genre(1, "Horror");
	}
}
