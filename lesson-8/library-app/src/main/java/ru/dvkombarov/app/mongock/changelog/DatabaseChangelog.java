package ru.dvkombarov.app.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "drop", author = "dvkom", runAlways = true)
    public void drop(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initDatabase", author = "dvkom", runAlways = true)
    public void initDatabase(MongoTemplate mongoTemplate) {
        Author author = new Author("Isaac Asimov");
        Author author2 = new Author("Jack London");
        Author author3 = new Author("Clifford Simak");

        Genre genre = new Genre("Science fiction");
        Genre genre2 = new Genre("Novel");

        Book book = mongoTemplate.save(new Book("The End of Eternity", author, genre));
        Book book2 = mongoTemplate.save(new Book("The Sea-Wolf", author2, genre2));
        Book book3 = mongoTemplate.save(new Book("White Fang", author2, genre2));
        Book book4 = mongoTemplate.save(new Book("Way Station", author3, genre));

        Comment comment = mongoTemplate.save(new Comment("Comment_1"));
        Comment comment2 = mongoTemplate.save(new Comment("Comment_2"));
        Comment comment3 = mongoTemplate.save(new Comment("Comment_3"));
        Comment comment4 = mongoTemplate.save(new Comment("Comment_4"));
        Comment comment5 = mongoTemplate.save(new Comment("Comment_5"));
        Comment comment6 = mongoTemplate.save(new Comment("Comment_6"));

        book.getComments().add(comment);
        book.getComments().add(comment2);
        book.getComments().add(comment3);
        book2.getComments().add(comment4);
        book3.getComments().add(comment5);
        book4.getComments().add(comment6);

        mongoTemplate.save(book);
        mongoTemplate.save(book2);
        mongoTemplate.save(book3);
        mongoTemplate.save(book4);
    }
}
