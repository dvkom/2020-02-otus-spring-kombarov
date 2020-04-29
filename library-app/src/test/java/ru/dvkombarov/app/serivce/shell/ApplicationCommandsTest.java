package ru.dvkombarov.app.serivce.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dvkombarov.app.domain.Book;
import ru.dvkombarov.app.domain.Comment;
import ru.dvkombarov.app.serivce.businnes.LibraryService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class
})
@ExtendWith(SpringExtension.class)
@DisplayName("Методы ApplicationCommands должны ")
@SpringBootTest
class ApplicationCommandsTest {

    private static final String COMMAND_GET_ALL_BOOKS = "get-books";
    private static final String COMMAND_GET_ALL_COMMENTS = "get-cmts";
    private static final String COMMAND_GET_BOOK = "get-book 1";
    private static final String COMMAND_GET_COMMENT = "get-cmt 1";
    private static final String COMMAND_DELETE_BOOK = "delete-book 1";
    private static final String COMMAND_ADD_BOOK = "add-book t 1 1 1";
    private static final String COMMAND_ADD_COMMENT = "add-cmt t 1";
    private static final String COMMAND_UPDATE_BOOK = "update-book 1 t 1 1 1";
    private static final String DONE_MESSAGE = "Сделано!";
    private static final String ERROR_MESSAGE = "Произошла ошибка...";
    private static final String NOT_FOUND_MESSAGE = "Не найдено";

    private Book mockBook = mock(Book.class);
    private Comment mockComment = mock(Comment.class);

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private Shell shell;

    @DisplayName("печатать информацию о книгах при команде get-books")
    @Test
    void getAllBooksShouldPrintBooks_whenBooksReceived() {
        doReturn("I am a book").when(mockBook).toString();
        doReturn(List.of(mockBook)).when(libraryService).getAllBooks();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL_BOOKS);
        assertThat(res).isEqualTo("I am a book");
    }

    @DisplayName("печатать сообщение об ошибке при Exception в get-books")
    @Test
    void getAllBooksShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).getAllBooks();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL_BOOKS);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать информацию о книге при команде get")
    @Test
    void getBookByIdShouldPrintBook_whenBookReceived() {
        doReturn("I am a book").when(mockBook).toString();
        doReturn(mockBook).when(libraryService).getBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET_BOOK);
        assertThat(res).isEqualTo("I am a book");
    }

    @DisplayName("печатать сообщение 'не найдено' при команде get")
    @Test
    void getBookByIdShouldPrintErrorMessage_whenExceptionHappened() {
        doReturn(null).when(libraryService).getBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET_BOOK);
        assertThat(res).isEqualTo(NOT_FOUND_MESSAGE);
    }

    @DisplayName("печатать Done message при команде delete-book")
    @Test
    void deleteBookByIdShouldPrintDoneMessage_whenBookDeleted() {
        doNothing().when(libraryService).deleteBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_DELETE_BOOK);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в delete-book")
    @Test
    void deleteBookByIdShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).deleteBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_DELETE_BOOK);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать Done message при команде add-book")
    @Test
    void addBookShouldPrintDoneMessage_whenBookAdded() {
        doReturn(new Book()).when(libraryService)
                .saveBook(anyString(), anyInt(), anyLong(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_ADD_BOOK);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в add-book")
    @Test
    void addBookShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService)
                .saveBook(anyString(), anyInt(), anyLong(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_ADD_BOOK);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать Done message при команде update-book")
    @Test
    void updateBookInfoShouldPrintDoneMessage_whenBookAdded() {
        doNothing().when(libraryService)
                .updateBookInfo(anyLong(), anyString(), anyInt(), anyLong(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_UPDATE_BOOK);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в команде update-book")
    @Test
    void updateBookInfoShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService)
                .updateBookInfo(anyLong(), anyString(), anyInt(), anyLong(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_UPDATE_BOOK);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать информацию о комментариях при команде get-cmts")
    @Test
    void getAllCommentsShouldPrintComments_whenCommentsReceived() {
        doReturn("I am a Comment").when(mockComment).toString();
        doReturn(List.of(mockComment)).when(libraryService).getAllComments();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL_COMMENTS);
        assertThat(res).isEqualTo("I am a Comment");
    }

    @DisplayName("печатать сообщение об ошибке при Exception в get-cmts")
    @Test
    void getAllCommentsShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).getAllComments();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL_COMMENTS);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать информацию о комментарие при команде get-cmt")
    @Test
    void getCommentByIdShouldPrintComment_whenCommentReceived() {
        doReturn("I am a Comment").when(mockComment).toString();
        doReturn(mockComment).when(libraryService).getCommentById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET_COMMENT);
        assertThat(res).isEqualTo("I am a Comment");
    }

    @DisplayName("печатать сообщение 'не найдено' при команде get-cmt")
    @Test
    void getCommentByIdShouldPrintErrorMessage_whenExceptionHappened() {
        doReturn(null).when(libraryService).getCommentById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET_COMMENT);
        assertThat(res).isEqualTo(NOT_FOUND_MESSAGE);
    }

    @DisplayName("печатать Done message при команде add-cmt")
    @Test
    void addCommentShouldPrintDoneMessage_whenCommentAdded() {
        doReturn(new Comment()).when(libraryService)
                .saveComment(anyString(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_ADD_COMMENT);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в add-cmt")
    @Test
    void addCommentShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService)
                .saveComment(anyString(), anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_ADD_COMMENT);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }
}