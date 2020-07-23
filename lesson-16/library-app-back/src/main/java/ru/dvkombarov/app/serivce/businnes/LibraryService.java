package ru.dvkombarov.app.serivce.businnes;

import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;

public interface LibraryService {
    Book getBookById(long id);

    List<Book> getAllBooks();

    void deleteBookById(long bookId);

    Book updateBook(long id, String title, int pageCount, String authorName,
                    String authorCountry, String genreName);

    Book saveBook(String title, int pageCount, String authorName,
                  String authorCountry, String genreName);

    Comment saveComment(String text, long bookId);

    List<Comment> getAllCommentsByBookId(long id);
}
