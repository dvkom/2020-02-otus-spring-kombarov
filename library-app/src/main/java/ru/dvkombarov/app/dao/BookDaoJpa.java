package ru.dvkombarov.app.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dvkombarov.app.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {

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
        Optional<Book> optionalBook = Optional.empty();
        try {
            TypedQuery<Book> query = em.createQuery("select b from Book b " +
                            "join fetch b.author " +
                            "join fetch b.genre " +
                            "left join fetch b.comments " +
                            "where b.id = :id",
                    Book.class);
            query.setParameter("id", id);
            optionalBook =  Optional.of(query.getSingleResult());
        } catch (NoResultException ignored) {
        }

        return optionalBook;
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "left join fetch b.comments ",
                Book.class);
        return query.getResultList();
    }
}
