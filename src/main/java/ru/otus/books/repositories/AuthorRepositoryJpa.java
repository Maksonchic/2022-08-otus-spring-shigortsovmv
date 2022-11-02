package ru.otus.books.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Author findById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    @Transactional
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Author findByNickName(final String nickName) {
        TypedQuery<Author> query
                = em.createQuery("select a from Author a where UPPER(a.nickName) = :NICKNAME", Author.class);
        query.setParameter("NICKNAME", nickName.toUpperCase());
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void remove(Author author) {
        em.remove(em.contains(author) ? author : em.merge(author));
    }
}
