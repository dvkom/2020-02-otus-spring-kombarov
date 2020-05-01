package ru.dvkombarov.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dvkombarov.app.domain.Comment;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        CriteriaQuery<Comment> cq = em.getCriteriaBuilder().createQuery(Comment.class);
        Root<Comment> rootEntry = cq.from(Comment.class);
        CriteriaQuery<Comment> all = cq.select(rootEntry);
        TypedQuery<Comment> allQuery = em.createQuery(all);

        return allQuery.getResultList();
    }
}
