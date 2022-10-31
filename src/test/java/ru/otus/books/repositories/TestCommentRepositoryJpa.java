package ru.otus.books.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@DisplayName("Тестируем комменты к книгам")
@Import(CommentRepositoryJpa.class)
public class TestCommentRepositoryJpa {

    @Autowired
    private CommentRepositoryJpa repo;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("Добавление комментария к первой книге")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void addCommentToFirstBook() {
        Book book = em.find(Book.class, 1L);
        List<Comment> comms = new ArrayList<>(){{
            add(new Comment(0, "first comment"));
            add(new Comment(0, "second comment"));
            add(new Comment(0, "third comment"));
        }};
        book.getComments().addAll(comms);
        em.persist(book);
        assertEquals(comms, em.find(Book.class, 1L).getComments());
    }

    @Test
    @DisplayName("Удаление комментариев второй книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void removeCommentsOfSecondBook() {
        Book book = em.find(Book.class, 2L);
        book.setComments(new ArrayList<>());
        em.persist(book);
        assertEquals(new ArrayList<>(), em.find(Book.class, 2L).getComments());
    }

    @Test
    @DisplayName("Редактирование комментария второй книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateCommentsOfSecondBook() {
        Book book = em.find(Book.class, 2L);
        String upd = "updated message text";
        book.getComments().get(0).setMessage(upd);
        em.persist(book);
        assertEquals(upd, em.find(Book.class, 2L).getComments().get(0).getMessage());
    }
}
