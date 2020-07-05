package ru.dvkombarov.app.serivce.rest.dto;

import java.util.Objects;

public class BookFullInfoDto {

    private final long id;
    private final String title;
    private final int pageCount;
    private final String authorName;
    private final String authorCountry;
    private final String genreName;

    public BookFullInfoDto(long id, String title, int pageCount, String authorName,
                           String authorCountry, String genreName) {
        this.id = id;
        this.title = title;
        this.pageCount = pageCount;
        this.authorName = authorName;
        this.authorCountry = authorCountry;
        this.genreName = genreName;
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

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorCountry() {
        return authorCountry;
    }

    public String getGenreName() {
        return genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookFullInfoDto)) return false;
        BookFullInfoDto that = (BookFullInfoDto) o;
        return id == that.id &&
                pageCount == that.pageCount &&
                Objects.equals(title, that.title) &&
                Objects.equals(authorName, that.authorName) &&
                Objects.equals(authorCountry, that.authorCountry) &&
                Objects.equals(genreName, that.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, pageCount, authorName,
                authorCountry, genreName);
    }

    @Override
    public String toString() {
        return "BookFullInfoDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", authorName='" + authorName + '\'' +
                ", authorCountry='" + authorCountry + '\'' +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
