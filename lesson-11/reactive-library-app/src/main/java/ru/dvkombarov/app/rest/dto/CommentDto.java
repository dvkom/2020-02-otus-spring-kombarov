package ru.dvkombarov.app.rest.dto;

import java.util.Objects;

public class CommentDto {

    private final String id;
    private final String text;
    private final String bookId;

    public CommentDto(String id, String text, String bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getBookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, bookId);
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
