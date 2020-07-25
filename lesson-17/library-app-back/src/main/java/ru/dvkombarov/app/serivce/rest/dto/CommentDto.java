package ru.dvkombarov.app.serivce.rest.dto;

import java.util.Objects;

public class CommentDto {

    private final long id;
    private final String text;
    private final long bookId;

    public CommentDto(long id, String text, long bookId) {
        this.id = id;
        this.text = text;
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getBookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto)) return false;
        CommentDto that = (CommentDto) o;
        return id == that.id &&
                bookId == that.bookId &&
                Objects.equals(text, that.text);
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
