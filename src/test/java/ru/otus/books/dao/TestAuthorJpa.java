package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Author;
import ru.otus.books.repositories.AuthorRepositoryJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Работаем с авторами")
@Import(AuthorRepositoryJpa.class)
class TestAuthorJpa {

	@Autowired
	private AuthorRepositoryJpa repo;

	@PersistenceContext
	private EntityManager tem;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Сравниваем различные способы получения первого автора")
	void compareFirst() {
		repo.save(new Author(0, "Michael", "l", "f", "www"));
		assertEquals(repo.findById(1), repo.findByNickName("Michael"));
		assertEquals(repo.findByNickName("michael"), repo.findByNickName("MichaEL"));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка автора")
	void insertNewOne() {
		// first check empty result
		assertNull(repo.findById(1));
		assertThrows(javax.persistence.NoResultException.class, () -> repo.findByNickName("me"));
		// add data
		repo.save(new Author(0, "me", "l", "f", "m"));
		// check inserted data
		assertNotNull(repo.findById(1));
		assertDoesNotThrow(() -> repo.findByNickName("me"));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Обновление первого автора")
	void updateFirst() {
		repo.save(new Author(0, "Michael", "l", "f", "www"));
		Author author = repo.findById(1);
		tem.detach(author);
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
		repo.remove(repo.findById(1));
		assertNull(repo.findById(1));
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление первого автора")
	void failedInsert() {
		repo.save(new Author(0, "Michael", "l", "f", "www"));
		assertThrows(
				javax.persistence.PersistenceException.class,
				() -> repo.save(
						repo.save(new Author(0, "Michael", "l", "f", "www"))));
	}
}
