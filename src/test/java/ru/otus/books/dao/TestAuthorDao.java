package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.books.domain.Author;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Работаем с авторами")
@Import({
		AuthorDaoJdbc.class,
		BookDaoJdbc.class
})
class TestAuthorDao {

	@Autowired
	private AuthorDao authorDao;

	@Test
	@DisplayName("Сравниваем различные способы получения первого автора")
	void compareFirst() {
		assertEquals(authorDao.getById(1), authorDao.getByNickName("Michael"));
		assertEquals(authorDao.getByNickName("michael"), authorDao.getByNickName("MichaEL"));
	}

	@Test
	@DisplayName("Вставка автора")
	void insertNewOne() {
		// first check empty result
		assertThrows(org.springframework.dao.EmptyResultDataAccessException.class, () -> authorDao.getById(3));
		assertNull(authorDao.getByNickName("me"));
		// add data
		authorDao.insert(new Author(0, "me", "l", "f", "m"));
		// check inserted data
		assertDoesNotThrow(() -> authorDao.getById(3));
		assertNotNull(authorDao.getByNickName("me"));
		assertNotNull(authorDao.getById(2));
	}

	@Test
	@DisplayName("Обновление первого автора")
	void updateFirst() {
		Author authorBefore = authorDao.getById(1);
		Author authorAfter = new Author(
				authorBefore.id(),
				authorBefore.nickname(),
				authorBefore.last_name(),
				authorBefore.first_name(),
				authorBefore.middle_name() + "_upd");
		authorDao.update(authorAfter);
		authorAfter = authorDao.getById(1);
		assertNotEquals(authorBefore, authorAfter);
	}

	@Test
	@DisplayName("Удаление первого автора")
	void deleteFirst() {
		// remove first
		Author author = authorDao.getById(1);
		assertNotNull(author);
		authorDao.remove(author);
		author = authorDao.getByNickName(author.nickname());
		assertNull(author);
		// check second
		author = authorDao.getById(2);
		assertNotNull(author);
	}
}
