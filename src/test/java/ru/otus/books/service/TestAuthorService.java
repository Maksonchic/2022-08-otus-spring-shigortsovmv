package ru.otus.books.service;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.models.Author;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import(AuthorDtoServiceImpl.class)
@DisplayName("Авторы")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TestAuthorService {

	@Autowired
	private AuthorDtoService authorService;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка")
	void insertNewOne() {
		AuthorDto author = AuthorDto.createDto(new Author(0, "nn", "ln", "fn", "mn"));
		authorService.add(author.getNickName(), author.getLastName(), author.getFirstName(), author.getMiddleName());
		assertThat(AuthorDto.createDto(mongoTemplate.findAll(Author.class).get(0))).isEqualTo(author);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление")
	void update() {
		insertAuthorViaMongoTemplate();
		authorService.removeByNickName("me");
		assertEquals(mongoTemplate.findAll(Author.class).size(), 0);
	}

	private void insertAuthorViaMongoTemplate() {
		BasicDBObjectBuilder bookWAB = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("title", "some title")
				.add("comments", new ArrayList<>());
		// add author
		DBObject author = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("nickName", "me")
				.add("books", new ArrayList<>(){{ add(bookWAB.get()); }})
				.get();
		mongoTemplate.save(author, "AUTHORS");
		// add book
		DBObject book = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("title", "some title")
				.add("comments", new ArrayList<>())
				.add("author", author)
				.get();
		mongoTemplate.save(book, "BOOKS");
	}
}
