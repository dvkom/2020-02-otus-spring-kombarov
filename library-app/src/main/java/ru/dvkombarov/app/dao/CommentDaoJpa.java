package ru.dvkombarov.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dvkombarov.app.domain.Comment;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CommentDaoJpa implements CommentDao {

    private static final String GRAPH = "javax.persistence.fetchgraph";

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

    @Override
    public List<Comment> getByBookId(long bookId) {
        EntityGraph eg = em.getEntityGraph("with-book-eg");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> rootEntry = cq.from(Comment.class);
        CriteriaQuery<Comment> all = cq.select(rootEntry)
                .where(cb.equal(rootEntry.get("book").get("id"), bookId));
        TypedQuery<Comment> allQuery = em.createQuery(all).setHint(GRAPH, eg);

        return allQuery.getResultList();
    }
}
