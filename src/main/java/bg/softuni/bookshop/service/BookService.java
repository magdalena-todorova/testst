package bg.softuni.bookshop.service;

import org.springframework.stereotype.Service;

import java.io.IOException;


public interface BookService {
    void seedBooks() throws IOException;
    boolean areBooksImported();

    void printAllBooksAfter2000();

    void printAllBooksByGeorgePowell();
}
