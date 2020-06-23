package ru.dvkombarov.app.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dvkombarov.app.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий комментариев должен ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DataMongoTest
class CommentRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("корректно соханять комментарий")
    @Test
    void shouldSaveCorrect() {
        Comment expected = new Comment("text");
        Mono<Comment> actual = commentRepository.save(expected);

        StepVerifier
                .create(actual)
                .assertNext(book -> assertThat(book).isEqualToComparingFieldByField(expected))
                .expectComplete()
                .verify();
    }

    @DisplayName("корректно получать комментарии")
    @Test
    void shouldGetAllCorrect() {
        mongoTemplate.save(new Comment("text"));

        StepVerifier
                .create(commentRepository.findAll())
                .assertNext(comment -> assertThat(comment).isNotNull())
                .expectComplete()
                .verify();
    }
}
