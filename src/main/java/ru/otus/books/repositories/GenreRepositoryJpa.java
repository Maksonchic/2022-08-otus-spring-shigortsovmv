package ru.otus.books.repositories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Genre findByGenre(final String genre) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where UPPER(g.genre) = :GENRE", Genre.class);
        query.setParameter("GENRE", genre.toUpperCase());
        return query.getSingleResult();
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    @Transactional
    public void remove(final Genre genre) {
        em.remove(em.contains(genre) ? genre : em.merge(genre));
    }
}
