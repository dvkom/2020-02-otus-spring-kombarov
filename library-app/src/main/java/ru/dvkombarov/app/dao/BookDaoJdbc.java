package ru.dvkombarov.app.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.dvkombarov.app.domain.Author;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Genre;
import ru.dvkombarov.app.exceptions.DaoOperationException;
import ru.dvkombarov.app.utils.DateUtils;
import ru.dvkombarov.app.utils.ValidateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void insert(Book book) {
        if (book == null || book.getAuthor() == null || book.getGenre() == null) {
            throw new DaoOperationException("Ошибка сохранения книги: " +
                    "должны быть заданы book, author и genre");
        }

        insertWithCheck(book);
    }

    @Override
    public void update(Book book) {
        if (ValidateUtils.isValid(book)) {
            throw new DaoOperationException("Ошибка обновления книги: " +
                    "должны быть заданы book, author и genre");
        }

        updateWithCheck(book);
    }

    @Override
    public Book getById(long id) {
        Book result = null;
        try {
            result = jdbc.queryForObject("" +
                            "select * from book " +
                            "left outer join author on book.author_id = author.id " +
                            "left outer join genre on book.genre_id = genre.id " +
                            "where book.id = :id",
                    Map.of("id", id), new BookMapper()
            );
        } catch (Exception ignored) {
        }

        return result;
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("" +
                        "select * from book " +
                        "left outer join author on book.author_id = author.id " +
                        "left outer join genre on book.genre_id = genre.id",
                new BookMapper()
        );
    }

    private void insertWithCheck(Book book) {
        if (getById(book.getId()) != null) {
            throw new DaoOperationException("Ошибка добавления книги: " +
                    "книга с таким Id уже существует");
        }
        if (getAuthorById(book.getAuthor().getId()) == null) {
            insertAuthor(book.getAuthor());
        }

        if (getGenreById(book.getGenre().getId()) == null) {
            insertGenre(book.getGenre());
        }
        insertBook(book);

    }

    private void updateWithCheck(Book book) {
        if (getById(book.getId()) == null) {
            throw new DaoOperationException("Ошибка обновления книги: " +
                    "книга с таким Id не существует");
        }

        updateBook(book);
        if (getAuthorById(book.getAuthor().getId()) != null) {
            updateAuthor(book.getAuthor());
        } else {
            insertAuthor(book.getAuthor());
        }

        if (getGenreById(book.getGenre().getId()) != null) {
            updateGenre(book.getGenre());
        } else {
            insertGenre(book.getGenre());
        }
    }

    private Author getAuthorById(long id) {
        Author result = null;
        try {
            result = jdbc.queryForObject("" +
                            "select * from author " +
                            "where id = :id",
                    Map.of("id", id), new AuthorMapper()
            );
        } catch (Exception ignored) {
        }

        return result;
    }

    private Genre getGenreById(long id) {
        Genre result = null;
        try {
            result = jdbc.queryForObject("" +
                            "select * from genre " +
                            "where id = :id",
                    Map.of("id", id), new GenreMapper()
            );
        } catch (Exception ignored) {}

        return result;
    }

    private void insertBook(Book book) {
        jdbc.update("insert into book " +
//                        "(id, title, page_count, author_id, genre_id) " +
                        "values " +
                        "(:id, :title, :pageCount, :authorId, :genreId)",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "pageCount", book.getPageCount(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId())
        );
    }

    private void insertAuthor(Author author) {
        if (ValidateUtils.isValid(author)) {
            jdbc.update("insert into author " +
//                            "(id, name, country, birth_date) " +
                            "values " +
                            "(:id, :name, :country, :birthDate)",
                    Map.of("id", author.getId(),
                            "name", author.getName(),
                            "country", author.getCountry(),
                            "birthDate", DateUtils.getStringFromDate(author.getBirthDate()))
            );
        }
    }

    private void insertGenre(Genre genre) {
        if (ValidateUtils.isValid(genre)) {
            jdbc.update("insert into genre " +
//                            "(id, name, description) " +
                            "values " +
                            "(:id, :name, :description)",
                    Map.of("id", genre.getId(),
                            "name", genre.getName(),
                            "description", genre.getDescription())
            );
        }
    }

    private void updateBook(Book book) {
        jdbc.update("" +
                        "update book " +
                        "set title = :title, page_count = :pageCount, " +
                        "author_id = :authorId, genre_id = :genreId " +
                        "where id = :id",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "pageCount", book.getPageCount(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId())
        );
    }

    private void updateAuthor(Author author) {
        if (ValidateUtils.isValid(author)) {
            jdbc.update("" +
                            "update author " +
                            "set name = :name, country = :country, birth_date = :birthDate " +
                            "where id = :id",
                    Map.of("id", author.getId(),
                            "name", author.getName(),
                            "country", author.getCountry(),
                            "birthDate", DateUtils.getStringFromDate(author.getBirthDate()))
            );
        }
    }

    private void updateGenre(Genre genre) {
        if (ValidateUtils.isValid(genre)) {
            jdbc.update("" +
                            "update genre " +
                            "set name = :name, description = :description " +
                            "where id = :id",
                    Map.of("id", genre.getId(),
                            "name", genre.getName(),
                            "description", genre.getDescription())
            );
        }
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long bookId = resultSet.getLong("id");
            String bookTitle = resultSet.getString("title");
            int bookPageCount = resultSet.getInt("page_count");
            long authorId = resultSet.getLong("author_id");
            long genreId = resultSet.getLong("genre_id");
            String authorName = resultSet.getString("author.name");
            String authorCountry = resultSet.getString("author.country");
            Date authorBirthDate = resultSet.getDate("author.birth_date");
            String genreName = resultSet.getString("genre.name");
            String genreDescription = resultSet.getString("genre.description");


            return new Book(bookId, bookTitle, bookPageCount,
                    new Author(authorId, authorName, authorCountry, authorBirthDate),
                    new Genre(genreId, genreName, genreDescription));
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            Date birthDate = resultSet.getDate("birth_date");

            return new Author(id, name, country, birthDate);
        }
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            return new Genre(id, name, description);
        }
    }
}
