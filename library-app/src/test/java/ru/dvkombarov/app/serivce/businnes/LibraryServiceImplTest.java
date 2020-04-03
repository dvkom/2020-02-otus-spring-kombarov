package ru.dvkombarov.app.serivce.businnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.dvkombarov.app.dao.BookDao;
import ru.dvkombarov.app.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Методы сервиса библиотеки должны ")
class LibraryServiceImplTest {

    @MockBean
    private BookDao bookDao;

    @Autowired
    private LibraryService libraryService;

    @Test
    @DisplayName("возвращять книгу по Id")
    void shouldReturnBookById() {
        Book book = new Book(1, null, 1, null, null);
        doReturn(book).when(bookDao)
                .getById(anyLong());
        assertThat(libraryService.getBookById(1)).isEqualTo(book);
    }

    @Test
    @DisplayName("возвращять все книги")
    void shouldReturnAllBooks() {
        List<Book> books = List.of(new Book(1, null, 1, null, null));
        doReturn(books).when(bookDao)
                .getAll();
        assertThat(libraryService.getAllBooks()).isEqualTo(books);
    }

    @Test
    @DisplayName("вызывать метод insert для книги")
    void shouldCallSaveBookMethod() {
        Book book = new Book(1, null, 1, null, null);
        libraryService.saveBook(book);
        verify(bookDao).insert(eq(book));
    }

    @Test
    @DisplayName("вызывать метод delete для id")
    void shouldCallDeleteBookMethod() {
        long id = 10;
        libraryService.deleteBookById(id);
        verify(bookDao).deleteById(id);
    }

    @Test
    @DisplayName("вызывать метод update для книги")
    void shouldCallUpdateBookMethod() {
        Book book = new Book(1, null, 1, null, null);
        libraryService.updateBookInfo(book);
        verify(bookDao).update(eq(book));
    }
}