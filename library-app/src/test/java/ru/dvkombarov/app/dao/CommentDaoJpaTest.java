package ru.dvkombarov.app.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями должен ")
@DataJpaTest
@Import(CommentDaoJpa.class)
public class CommentDaoJpaTest {

    @Autowired
    private CommentDaoJpa commentDaoJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать информацию о нужном комментарии по его id")
    @Test
    void shouldFindExpectedCommentById() {
        Optional<Comment> optionalActualComment = commentDaoJpa.getById(1L);
        Comment expectedComment = em.find(Comment.class, 1L);
        assertThat(optionalActualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @DisplayName("загружать список всех комментариев с полной информацией о них")
    @Test
    void shouldReturnCorrectCommentsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Comment> comments = commentDaoJpa.getAll();
        assertThat(comments).isNotNull().hasSize(2)
                .allMatch(s -> !s.getText().equals(""))
                .allMatch(s -> s.getBook() != null);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }

    @DisplayName("корректно сохранять всю информацию о комментарии")
    @Test
    void shouldSaveAllCommentInfo() {
        Comment comment = new Comment(0, "test", new Book());
        commentDaoJpa.insert(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull()
                .matches(s -> s.getText().equals(comment.getText()))
                .matches(s -> s.getBook() != null && s.getBook().equals(comment.getBook()));
    }
}
