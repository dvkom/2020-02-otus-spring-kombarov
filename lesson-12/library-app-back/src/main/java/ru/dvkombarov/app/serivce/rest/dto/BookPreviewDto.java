package ru.dvkombarov.app.serivce.rest.dto;

import java.util.Objects;

public class BookPreviewDto {

    private final long id;
    private final String title;
    private final int pageCount;
    private final String authorName;

    public BookPreviewDto(long id, String title, int pageCount, String authorName) {
        this.id = id;
        this.title = title;
        this.pageCount = pageCount;
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getAuthorName() {
        return authorName;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookPreviewDto)) return false;
        BookPreviewDto that = (BookPreviewDto) o;
        return pageCount == that.pageCount &&
                id == that.id &&
                Objects.equals(title, that.title) &&
                Objects.equals(authorName, that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pageCount, authorName);
    }

    @Override
    public String toString() {
        return "BookPreviewDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", authorName='" + authorName + '\'' +
                '}';
    }
}
