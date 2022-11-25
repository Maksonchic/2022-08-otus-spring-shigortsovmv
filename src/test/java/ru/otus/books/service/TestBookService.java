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
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import(BookDtoServiceImpl.class)
@DisplayName("Книги")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TestBookService {

	@Autowired
	private BookDtoService bookService;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	@DisplayName("Вставка")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void checkInsert() {
		insertBookViaMongoTemplate();
		bookService.add("New one", 123, "me", "comedy");
		List<Book> all = mongoTemplate.findAll(Book.class);
		assertEquals(all.size(), 2);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка коммента")
	public void addComment() {
		insertBookViaMongoTemplate();
		String commentText = "some comment new";
		Comment newComment = new Comment("0", commentText);
		List<Comment> bookComments = bookService.getBookComments(2);
		bookComments = List.of(bookComments.get(0), bookComments.get(1), newComment);
		bookService.addBookComment(2, newComment.getMessage());
		assertEquals(Objects.requireNonNull(mongoTemplate.findById(2, Book.class)).getComments().size(), bookComments.size());
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Получение комментов")
	public void getComments() {
		insertBookViaMongoTemplate();
		List<Comment> bookComments = bookService.getBookComments(2);
		assertEquals(Objects.requireNonNull(mongoTemplate.findById(2, Book.class)).getComments(), bookComments);
	}

	private void insertBookViaMongoTemplate() {
		// add genre
		DBObject genre = BasicDBObjectBuilder.start().add("_id", 1).add("genre", "Comedy").get();
		mongoTemplate.save(genre, "GENRES");
		// add comments
		DBObject comment1 = BasicDBObjectBuilder.start().add("_id", "1").add("message", "comment 1").get();
		DBObject comment2 = BasicDBObjectBuilder.start().add("_id", "2").add("message", "comment 2").get();
		// create book without author
		DBObject book = BasicDBObjectBuilder.start()
				.add("_id", 2)
				.add("title", "some title")
				.add("author", 1)
				.add("genre", genre)
				.add("comments", new ArrayList<>() {{
					add(comment1);
					add(comment2);
				}}).get();
		// add author
		DBObject author = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("nickName", "me")
				.add("books", new ArrayList<>(){{ add(book); }})
				.get();
		mongoTemplate.save(author, "AUTHORS");
		mongoTemplate.save(book, "BOOKS");
	}
}
