package ru.dvkombarov.app.serivce.businnes;

import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;

public interface LibraryService {
    Book getBookById(String id);
    List<Book> getAllBooks();
    Book saveBook(String title, String authorName, String genreName);
    void deleteBookById(String bookId);
    Comment saveComment(String text, String bookId);
    Comment getCommentById(String id);
    List<Comment> getAllCommentsByBookId(String id);
    List<Comment> getAllComments();
}
