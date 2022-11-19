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
import ru.otus.books.models.Comment;

import java.util.ArrayList;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataMongoTest
@Import(CommentDtoServiceImpl.class)
@DisplayName("Комменты")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TestCommentService {

	@Autowired
	private CommentDtoService commentService;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Удаление")
	void testDeleteOne() {
		long commentId = 3;
		insertCommentViaMongoTemplate(commentId);
		assertThat(mongoTemplate.findAll(DBObject.class, "COMMENTS"))
				.extracting("_id")
				.containsOnly(commentId);
		commentService.removeComment(commentId);
		assertNull(mongoTemplate.findById(commentId, Comment.class));
	}

	private void insertCommentViaMongoTemplate(long commentId) {
		// add comment
		DBObject comment = BasicDBObjectBuilder.start().add("_id", commentId).add("message", "abc").get();
		mongoTemplate.save(comment, "COMMENTS");
		// create book without author
		BasicDBObjectBuilder bookWAB = BasicDBObjectBuilder.start()
				.add("_id", 1)
				.add("title", "some title")
				.add("comments", new ArrayList<>() {{
					add(commentId);
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
				.add("_id", 1)
				.add("title", "some title")
				.add("comments", new ArrayList<>() {{
					add(commentId);
				}}).add("author", author).get();
		mongoTemplate.save(book, "BOOKS");
	}

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Изменение")
	void testEdit() {
		DBObject comment = BasicDBObjectBuilder
				.start()
				.add("_id", 38)
				.add("message", "abc")
				.get();
		mongoTemplate.save(comment, "COMMENTS");
		assertThat(mongoTemplate.findAll(DBObject.class, "COMMENTS"))
				.extracting("_id")
				.containsOnly(38);

		String newText = "new text";
		commentService.edit(38, newText);

		assertEquals(Objects.requireNonNull(mongoTemplate.findById(38, Comment.class)).getMessage(), newText);
	}
}

