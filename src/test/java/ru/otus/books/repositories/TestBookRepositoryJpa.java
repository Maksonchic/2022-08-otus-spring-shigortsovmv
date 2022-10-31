package ru.otus.books.repositories;

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@DisplayName("Работаем с книгами")
@Import(BookRepositoryJpa.class)
class TestBookRepositoryJpa {

	@Autowired
	private BookRepositoryJpa repo;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставляем книгу с комментами")
	void insertNewBook() {
		Author aNew = new Author(1, "Michael", "Last", "Firstov", "Middleich");
		Genre gNew = new Genre(1, "Horror");
		Book bNew3 = new Book(555, "ццццц", 41414, aNew, gNew, new ArrayList<>());
		bNew3.getComments().add(new Comment(2, "a"));
		bNew3.getComments().add(new Comment(3, "b"));
		bNew3.getComments().add(new Comment(4, "d"));
		repo.save(bNew3);
		System.out.println(em.find(Book.class, 1L));
		System.out.println(em.find(Book.class, 3L));
		bNew3.setId(3);
		assertEquals(bNew3, em.find(Book.class, 3L));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Изменение книги")
	void updateBook() {
		Book bNew = em.find(Book.class, 1L);
		assertNotNull(bNew);
		String newTitle = "new title + " + bNew.getTitle();
		bNew.setTitle(newTitle);
		repo.save(bNew);
		assertEquals(newTitle, em.find(Book.class, 1L).getTitle());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление книги")
	void removeBook() {
		repo.remove(em.find(Book.class, 1L));
		assertNull(em.find(Book.class, 1L));
	}

	@Test
	@DisplayName("Каскад")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
	public void massRemoveByBook() {
		Book book = em.find(Book.class, 2L);
		em.remove(book);
		assertNull(em.find(Book.class, 2L));
		assertNull(em.find(Comment.class, 1L));
		assertNotNull(em.find(Author.class, 1L));
		assertNotNull(em.find(Genre.class, 1L));
	}
}
