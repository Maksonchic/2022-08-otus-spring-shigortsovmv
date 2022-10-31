package ru.otus.books.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

//    @Autowired
//    private AuthorRepositoryJpa authorJpa;
//
//    @Autowired
//    private GenreRepositoryJpa genreJpa;
//
//    @Autowired
//    private CommentRepositoryJpa commentJpa;

    @Override
    public Book save(final Book book) {
//        saveRefers(book);
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    private void saveRefers(final Book book) {
//        authorJpa.save(book.getAuthor());
//        genreJpa.save(book.getGenre());
//        book.getComments().forEach(c -> commentJpa.save(c));
    }

    @Override
    @Transactional
    public List<Book> findAll() {
        return em.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Override
    @Transactional
    public Optional<Book> findById(long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Override
    public void remove(Book book) {
        em.remove(book);
    }
}
