package ru.dvkombarov.app.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "page_count")
    private int pageCount;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Transient
    private boolean isEdited;

    @Transient
    private boolean isDesigned;

    public Book() {
    }

    public Book(String title, int pageCount, String author, String genre) {
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
        this.genre = genre;
    }

    public Book(Book book) {
        this.title = book.getTitle();
        this.pageCount = book.getPageCount();
        this.author = book.getAuthor();
        this.genre = book.getGenre();
        this.isEdited = book.isEdited();
        this.isDesigned = book.isDesigned();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public boolean isDesigned() {
        return isDesigned;
    }

    public void setDesigned(boolean designed) {
        isDesigned = designed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id &&
                pageCount == book.pageCount &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(isEdited, book.isEdited) &&
                Objects.equals(isDesigned, book.isDesigned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pageCount, author, genre, isEdited, isDesigned);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", isEdited='" + isEdited + '\'' +
                ", isDesigned='" + isDesigned + '\'' +
                '}';
    }
}
