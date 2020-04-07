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

    private static final String COMMAND_GET_ALL = "get-all";
    private static final String COMMAND_GET = "get 1";
    private static final String COMMAND_DELETE = "delete 1";
    private static final String COMMAND_ADD = "add 1 t 1 1 1";
    private static final String COMMAND_UPDATE = "update 1 t 1 1 1";
    private static final String DONE_MESSAGE = "Сделано!";
    private static final String ERROR_MESSAGE = "Произошла ошибка...";
    private static final String NOT_FOUND_MESSAGE = "Книга не найдена";

    private Book mockBook = mock(Book.class);

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private Shell shell;

    @DisplayName("печатать информацию о книгах при команде get-all")
    @Test
    void getAllBooksShouldPrintBooks_whenBooksReceived() {
        doReturn("I am a book").when(mockBook).toString();
        doReturn(List.of(mockBook)).when(libraryService).getAllBooks();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL);
        assertThat(res).isEqualTo("I am a book");
    }

    @DisplayName("печатать сообщение об ошибке при Exception в get-all")
    @Test
    void getAllBooksShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).getAllBooks();
        String res = (String) shell.evaluate(() -> COMMAND_GET_ALL);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать информацию о книге при команде get")
    @Test
    void getBookByIdShouldPrintBook_whenBookReceived() {
        doReturn("I am a book").when(mockBook).toString();
        doReturn(mockBook).when(libraryService).getBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET);
        assertThat(res).isEqualTo("I am a book");
    }

    @DisplayName("печатать сообщение 'книга не найдена' при команде get")
    @Test
    void getBookByIdShouldPrintErrorMessage_whenExceptionHappened() {
        doReturn(null).when(libraryService).getBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_GET);
        assertThat(res).isEqualTo(NOT_FOUND_MESSAGE);
    }

    @DisplayName("печатать Done message при команде delete")
    @Test
    void deleteBookByIdShouldPrintDoneMessage_whenBookDeleted() {
        doNothing().when(libraryService).deleteBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_DELETE);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в delete")
    @Test
    void deleteBookByIdShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).deleteBookById(anyLong());
        String res = (String) shell.evaluate(() -> COMMAND_DELETE);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("печатать Done message при команде add")
    @Test
    void addBookShouldPrintDoneMessage_whenBookAdded() {
        doNothing().when(libraryService).saveBook(any(Book.class));
        String res = (String) shell.evaluate(() -> COMMAND_ADD);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("печатать сообщение об ошибке при Exception в add")
    @Test
    void addBookShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).saveBook(any(Book.class));
        String res = (String) shell.evaluate(() -> COMMAND_ADD);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }

    @DisplayName("должен печатать Done message при команде update")
    @Test
    void updateBookInfoShouldPrintDoneMessage_whenBookAdded() {
        doNothing().when(libraryService).updateBookInfo(any(Book.class));
        String res = (String) shell.evaluate(() -> COMMAND_UPDATE);
        assertThat(res).isEqualTo(DONE_MESSAGE);
    }

    @DisplayName("должен печатать сообщение об ошибке при Exception в команде update")
    @Test
    void updateBookInfoShouldPrintErrorMessage_whenExceptionHappened() {
        doThrow(RuntimeException.class).when(libraryService).updateBookInfo(any(Book.class));
        String res = (String) shell.evaluate(() -> COMMAND_UPDATE);
        assertThat(res).isEqualTo(ERROR_MESSAGE);
    }
}