package ru.dvkombarov.app.domain.migration;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "books")
public class MongoBook {

    @Id
    private String id;

    private String title;

    @DBRef
    private MongoAuthor author;

    @DBRef
    private MongoGenre genre;

    private List<MongoComment> comments;

    public MongoBook() {
    }

    public MongoBook(String title, MongoAuthor author, MongoGenre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MongoAuthor getAuthor() {
        return author;
    }

    public void setAuthor(MongoAuthor author) {
        this.author = author;
    }

    public MongoGenre getGenre() {
        return genre;
    }

    public void setGenre(MongoGenre genre) {
        this.genre = genre;
    }

    public List<MongoComment> getComments() {
        return comments;
    }

    public void setComments(List<MongoComment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "MongoBook{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", genre=" + genre +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MongoBook)) return false;
        MongoBook book = (MongoBook) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, genre);
    }
}
