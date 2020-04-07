package ru.dvkombarov.app.domain;

import java.util.Date;

public class Author {

    private final long id;
    private final String name;
    private final String country;
    private final Date birthDate;

    public Author(long id, String name, String country, Date birthDate) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
