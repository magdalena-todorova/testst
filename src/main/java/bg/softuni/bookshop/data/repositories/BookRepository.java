package bg.softuni.bookshop.data.repositories;

import bg.softuni.bookshop.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findAllByReleaseDateAfter(LocalDate date);
    Set<Book> findBooksByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}
