package ru.dvkombarov.app.domain;

import java.util.Date;
import java.util.Objects;

public class PublicationResult {
    private String bookTitle;
    private String bookAuthor;
    private Date publicationDate;
    private Result result;

    public enum Result {
        SUCCESS,
        FAILED
    }

    public PublicationResult(String bookTitle, String bookAuthor, Result result) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.result = result;
        this.publicationDate = result == Result.SUCCESS ? new Date() : null;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicationResult)) return false;
        PublicationResult that = (PublicationResult) o;
        return Objects.equals(bookTitle, that.bookTitle) &&
                Objects.equals(bookAuthor, that.bookAuthor) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                result == that.result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookTitle, bookAuthor, publicationDate, result);
    }

    @Override
    public String toString() {
        return "PublicationResult{" +
                "bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", result=" + result +
                '}';
    }
}
