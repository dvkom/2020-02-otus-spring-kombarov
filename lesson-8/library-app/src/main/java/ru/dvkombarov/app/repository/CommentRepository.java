package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dvkombarov.app.domain.Comment;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Optional<Comment> getById(String id);
}
