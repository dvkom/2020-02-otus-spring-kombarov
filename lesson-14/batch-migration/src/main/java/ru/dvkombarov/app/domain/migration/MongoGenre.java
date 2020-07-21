package ru.dvkombarov.app.domain.migration;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "genres")
public class MongoGenre {

    @Id
    private String id;

    private String name;

    public MongoGenre() {
    }

    public MongoGenre(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MongoGenre)) return false;
        MongoGenre that = (MongoGenre) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MongoGenre{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
