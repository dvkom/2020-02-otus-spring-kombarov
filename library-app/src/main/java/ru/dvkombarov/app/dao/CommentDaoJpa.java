package ru.dvkombarov.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dvkombarov.app.domain.Comment;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() != 0) {
            return em.merge(comment);
        } else {
            em.persist(comment);
            return comment;
        }
    }

    @Override
    public Optional<Comment> getById(long id) {
        Optional<Comment> optionalComment = Optional.empty();
        try {
            TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                            "join fetch c.book " +
                            "where c.id = :id",
                    Comment.class);
            query.setParameter("id", id);
            optionalComment =  Optional.of(query.getSingleResult());
        } catch (NoResultException ignored) {
        }

        return optionalComment;
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c " +
                        "join fetch c.book ",
                Comment.class);
        return query.getResultList();
    }
}
