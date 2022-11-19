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
import ru.otus.books.dto.CommentDto;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;

import java.util.ArrayList;
import java.util.List;

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
		assertEquals(mongoTemplate.findAll(Book.class).size(), 2);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Вставка коммента")
	public void addComment() {
		insertBookViaMongoTemplate();
		String commentText = "some comment new";
		CommentDto newComment = new CommentDto(0, commentText);
		List<CommentDto> bookComments = bookService.getBookComments(2);
		bookComments = List.of(bookComments.get(0), bookComments.get(1), newComment);
		bookService.addBookComment(2, newComment.getMessage());
		assertEquals(
				CommentDto.createDto(mongoTemplate.findAll(Comment.class)),
				bookComments);
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Получение комментов")
	public void getComments() {
		insertBookViaMongoTemplate();
		List<CommentDto> bookComments = bookService.getBookComments(2);
		assertEquals(
				CommentDto.createDto(mongoTemplate.findAll(Comment.class)),
				bookComments);
	}

	private void insertBookViaMongoTemplate() {
		// add genre
		DBObject genre = BasicDBObjectBuilder.start().add("_id", 1).add("genre", "Comedy").get();
		mongoTemplate.save(genre, "GENRES");
		// add comments
		DBObject comment1 = BasicDBObjectBuilder.start().add("_id", 1).add("message", "comment 1").get();
		DBObject comment2 = BasicDBObjectBuilder.start().add("_id", 2).add("message", "comment 2").get();
		mongoTemplate.save(comment1, "COMMENTS");
		mongoTemplate.save(comment2, "COMMENTS");
		// create book without author
		BasicDBObjectBuilder bookWAB = BasicDBObjectBuilder.start()
				.add("_id", 2)
				.add("title", "some title")
				.add("genre", genre)
				.add("comments", new ArrayList<>() {{
					add(1);
					add(2);
				}});
		// add author
		DBObject author = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("nickName", "me")
				.add("books", new ArrayList<>(){{ add(bookWAB.get()); }})
				.get();
		mongoTemplate.save(author, "AUTHORS");
		// add book
		DBObject book = BasicDBObjectBuilder.start()
				.add("_id", 2)
				.add("title", "some title")
				.add("genre", genre)
				.add("comments", new ArrayList<>() {{
					add(1);
					add(2);
				}}).add("author", author).get();
		mongoTemplate.save(book, "BOOKS");
	}
}
