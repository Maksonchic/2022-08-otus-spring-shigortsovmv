package ru.otus.books.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    @Transactional
    public void remove(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
