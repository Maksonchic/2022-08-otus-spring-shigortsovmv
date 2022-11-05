package ru.otus.books.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.books.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorRepositoryJpa implements AuthorRepository {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Author findByNickName(final String nickName) {
        TypedQuery<Author> query
                = em.createQuery("select a from Author a where UPPER(a.nickName) = :NICKNAME", Author.class);
        query.setParameter("NICKNAME", nickName.toUpperCase());
        return query.getSingleResult();
    }

    @Override
    public void remove(Author author) {
        em.remove(em.contains(author) ? author : em.merge(author));
    }
}
