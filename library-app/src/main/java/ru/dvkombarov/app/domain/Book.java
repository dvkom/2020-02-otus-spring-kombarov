package ru.dvkombarov.app.domain;

public class Book {

    private final long id;
    private final String title;
    private final int pageCount;
    private final Author author;
    private final Genre genre;

    public Book(long id, String title, int pageCount, Author author, Genre genre) {
        this.id = id;
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
        this.genre = genre;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", author=" + author +
                ", genre=" + genre +
                '}';
    }
}
