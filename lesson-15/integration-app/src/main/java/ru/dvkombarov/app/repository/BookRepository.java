package ru.dvkombarov.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dvkombarov.app.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
