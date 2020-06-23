package ru.dvkombarov.app.rest.dto;

import java.util.Objects;

public class BookDto {

    private final String id;
    private final String title;
    private final String authorName;
    private final String genreName;

    public BookDto(String id, String title, String authorName, String genreName) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.genreName = genreName;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDto)) return false;
        BookDto that = (BookDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(authorName, that.authorName) &&
                Objects.equals(genreName, that.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authorName, genreName);
    }

    @Override
    public String toString() {
        return "BookFullInfoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
