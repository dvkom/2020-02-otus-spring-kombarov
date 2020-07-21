package ru.dvkombarov.app.domain.migration;

import java.util.Objects;

public class MongoComment {

    private String text;

    public MongoComment() {
    }

    public MongoComment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MongoComment)) return false;
        MongoComment comment = (MongoComment) o;
        return Objects.equals(text, comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
