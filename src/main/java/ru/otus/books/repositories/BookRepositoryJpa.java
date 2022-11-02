package ru.otus.books.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Book save(final Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Override
    @Transactional
    public void remove(final Book book) {
        em.remove(em.contains(book) ? book : em.merge(book));
    }

    @Override
    public List<Book> findByAuthor(final Author author) {
        TypedQuery<Book> query
                = em.createQuery("select b from Book b, Author a where b.author = a and a = :AUTHOR", Book.class);
        query.setParameter("AUTHOR", author);
        EntityGraph<?> entityGraph = em.getEntityGraph("books-entity-graph");
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }
}
