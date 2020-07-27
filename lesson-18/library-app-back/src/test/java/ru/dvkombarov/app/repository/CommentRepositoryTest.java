package ru.dvkombarov.app.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами должен ")
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать список всех комментариев по id книги")
    @Test
    void shouldReturnCorrectCommentsListByBookId() {
        List<Comment> comments = commentRepository.getByBookId(1L);

        assertThat(comments).isNotNull().hasSize(1)
                .allMatch(s -> s.getText().equals("Text1"));
    }

    @DisplayName("корректно сохранять всю информацию о комментарии")
    @Test
    void shouldSaveAllCommentInfo() {
        Comment comment = new Comment(0, "test", new Book());
        commentRepository.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull()
                .matches(s -> s.getText().equals(comment.getText()))
                .matches(s -> s.getBook() != null && s.getBook().equals(comment.getBook()));
    }
}
