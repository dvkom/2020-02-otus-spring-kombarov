package ru.dvkombarov.app.serivce.businnes;

import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;

import java.util.List;

public interface LibraryService {
    Book getBookById(long id);
    List<Book> getAllBooks();
    Book saveBook(String title, int pageCount, long authorId, long genreId);
    void deleteBookById(long bookId);
    void updateBookInfo(long id, String title, int pageCount, long authorId, long genreId);

    Comment saveComment(String text, long bookId);

    Comment getCommentById(long id);

    List<Comment> getAllComments();
}
