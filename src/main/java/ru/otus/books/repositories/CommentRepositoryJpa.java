package ru.otus.books.repositories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
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
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    @Transactional
    public void remove(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }

    @Override
    @Transactional
    public void removeById(long id) {
        Comment comment = em.find(Comment.class, id);
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
