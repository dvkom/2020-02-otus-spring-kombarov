package ru.dvkombarov.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.dvkombarov.app.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
