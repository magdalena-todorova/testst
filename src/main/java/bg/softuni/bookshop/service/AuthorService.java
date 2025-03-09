package bg.softuni.bookshop.service;

import java.io.IOException;

public interface AuthorService {
    void seedAuthors() throws IOException;
    boolean areAuthorsImported();
    void printAllAuthorsWith1BookBefore1990();

    void printAllAuthorsWithDescCount();
}
