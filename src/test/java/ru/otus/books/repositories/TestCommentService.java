package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.service.CommentDtoService;
import ru.otus.books.service.CommentDtoServiceImpl;

@DataJpaTest
@Import(CommentDtoServiceImpl.class)
@DisplayName("Работаем с комментами")
class TestCommentService {

	@Autowired
	private CommentDtoService commentDtoService;

	@Test
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@DisplayName("Тест конвертации в ДТО")
	void testConvertEntityDto() {
	}
}
