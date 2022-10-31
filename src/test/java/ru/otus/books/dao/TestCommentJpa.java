package ru.otus.books.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Book;
import ru.otus.books.repositories.CommentRepositoryJpa;

import static ru.otus.books.dao.utils.DataCreator.createDataFirstBook;

@DataJpaTest
@DisplayName("Тестируем комменты к книгам")
@Import(CommentRepositoryJpa.class)
public class TestCommentJpa {

    @Autowired
    private CommentRepositoryJpa repo;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addFirstBookComments() {
        Book b = createDataFirstBook();
    }

}
