package ru.dvkombarov.app.dao;

import ru.dvkombarov.app.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Comment insert(Comment comment);
    Optional<Comment> getById(long id);
    List<Comment> getAll();
}
