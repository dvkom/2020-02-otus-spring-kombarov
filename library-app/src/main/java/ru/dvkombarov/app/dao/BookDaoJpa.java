package ru.dvkombarov.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dvkombarov.app.domain.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {

    private static final String GRAPH = "javax.persistence.fetchgraph";

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book insert(Book book) {
        if (book.getId() != 0) {
            return em.merge(book);
        } else {
            em.persist(book);
            return book;
        }
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        EntityGraph eg = em.getEntityGraph("with-author-and-genre-eg");

        return Optional.ofNullable(em.find(Book.class, id, Map.of(GRAPH, eg)));
    }

    @Override
    public void deleteById(long id) {
        this.getById(id).ifPresent(em::remove);
    }

    @Override
    public List<Book> getAll() {
        EntityGraph eg = em.getEntityGraph("with-author-and-genre-eg");
        CriteriaQuery<Book> cq = em.getCriteriaBuilder().createQuery(Book.class);
        Root<Book> rootEntry = cq.from(Book.class);
        CriteriaQuery<Book> all = cq.select(rootEntry);
        TypedQuery<Book> allQuery = em.createQuery(all).setHint(GRAPH, eg);

        return allQuery.getResultList();
    }
}
