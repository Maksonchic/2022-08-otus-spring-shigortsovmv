package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Работаем с авторами")
@Import(AuthorRepositoryJpa.class)
class TestAuthorRepositoryJpa {

	@Autowired
	private AuthorRepositoryJpa repo;

	@PersistenceContext
	private EntityManager em;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Сравниваем различные способы получения первого автора")
	void compareFirst() {
		Author a1 = em.find(Author.class, 1L);
		assertEquals(a1, repo.findById(1));
		assertEquals(a1, repo.findByNickName("Michael"));
		assertEquals(a1, repo.findByNickName("MiChaEL"));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка автора")
	void insertNewOne() {
		repo.save(new Author(0, "me", "l", "f", "m"));
		assertNotNull(em.find(Author.class, 2L));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Обновление первого автора")
	void updateFirst() {
		repo.save(new Author(0, "Michael", "l", "f", "www"));
		Author author = repo.findById(1);
		em.detach(author);
		Author updatedAuthor = new Author(
				author.getId(),
				author.getNickName(),
				author.getLastName() + "_upd",
				author.getFirstName(),
				author.getMiddleName());
		repo.save(updatedAuthor);
		assertNotEquals(author, repo.findById(1));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление первого автора")
	void deleteFirst() {
		repo.save(new Author(0, "Michael", "l", "f", "www"));
		repo.save(new Author(0, "Michael1", "l", "f", "www"));
		repo.save(new Author(0, "Michael2", "l", "f", "www"));
		repo.remove(em.find(Author.class, 1L));
		assertNull(em.find(Author.class, 1L));
		assertNotNull(em.find(Author.class, 2L));
		assertNotNull(em.find(Author.class, 3L));
	}
}
