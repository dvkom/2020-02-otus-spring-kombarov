package ru.dvkombarov.app.domain.migration;

import java.util.Objects;

public class MongoGenre {

    private String name;

    public MongoGenre() {
    }

    public MongoGenre(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MongoGenre)) return false;
        MongoGenre genre = (MongoGenre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
