package ru.otus.books.service;

import com.mongodb.DBObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(GenreDtoServiceImpl.class)
@DisplayName("Жанры")
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class TestGenreService {

	@Autowired
	private GenreDtoService genreService;

	@Autowired
	MongoTemplate mongoTemplate;

	@Test
	@DisplayName("Вставка")
	void testInsert() {
		genreService.add("Comedy");
		assertThat(mongoTemplate.findAll(DBObject.class, "GENRES"))
				.extracting("genre")
				.containsOnly("Comedy");
	}
}
