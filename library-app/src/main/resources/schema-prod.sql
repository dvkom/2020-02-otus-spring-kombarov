DROP TABLE IF EXISTS author;
CREATE TABLE author (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    birth_date DATE,
    CONSTRAINT unique_author UNIQUE (name, country, birth_date)
);

DROP TABLE IF EXISTS genre;
CREATE TABLE genre (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);
DROP TABLE IF EXISTS book;
CREATE TABLE book (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    page_count INT,
    author_id BIGINT NOT NULL REFERENCES author(id),
    genre_id BIGINT NOT NULL REFERENCES genre(id),
    CONSTRAINT unique_book UNIQUE (title, author_id)
);