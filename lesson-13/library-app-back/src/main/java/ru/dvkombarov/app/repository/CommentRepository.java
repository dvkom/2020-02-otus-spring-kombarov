package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "with-book-eg", type = EntityGraph.EntityGraphType.LOAD)
    List<Comment> getByBookId(long bookId);
}
