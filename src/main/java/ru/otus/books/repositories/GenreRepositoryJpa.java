package ru.otus.books.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.books.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        return em.merge(genre);
    }

    @Override
    public Genre findByGenre(final String genre) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where UPPER(g.genre) = :GENRE", Genre.class);
        query.setParameter("GENRE", genre.toUpperCase());
        return query.getSingleResult();
    }

    @Override
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public void remove(Genre genre) {

    }
}
