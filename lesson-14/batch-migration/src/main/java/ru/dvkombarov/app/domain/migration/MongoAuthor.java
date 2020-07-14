package ru.dvkombarov.app.domain.migration;

import java.util.Objects;

public class MongoAuthor {

    private String name;

    public MongoAuthor() {
    }

    public MongoAuthor(String name) {
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
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MongoAuthor)) return false;
        MongoAuthor author = (MongoAuthor) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
