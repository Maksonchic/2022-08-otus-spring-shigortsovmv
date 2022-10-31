package ru.otus.books.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.books.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @Autowired
    private EntityManager em;

    @Override
    public Author findById(long id) {
        return em.find(Author.class, id);
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
        em.remove(author);
    }
}
