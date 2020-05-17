package ru.dvkombarov.app.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.dvkombarov.app.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void getByIdTest() {
        Comment expected = mongoTemplate.save(new Comment("name"));
        Comment actual = commentRepository.getById(expected.getId()).orElse(new Comment());

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
}
