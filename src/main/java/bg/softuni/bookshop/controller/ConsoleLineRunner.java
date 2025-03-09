package bg.softuni.bookshop.controller;

import bg.softuni.bookshop.service.AuthorService;
import bg.softuni.bookshop.service.BookService;
import bg.softuni.bookshop.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleLineRunner implements CommandLineRunner {
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;
    public ConsoleLineRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        //this.bookService.printAllBooksAfter2000();
        //this.authorService.printAllAuthorsWith1BookBefore1990();
        //this.authorService.printAllAuthorsWithDescCount();
        this.bookService.printAllBooksByGeorgePowell();
    }

    private void seedData() throws IOException {
        if(!this.categoryService.areImported()){
            this.categoryService.seedCategories();
        }
        if(!this.authorService.areAuthorsImported()){
            this.authorService.seedAuthors();
        }
        if(!this.bookService.areBooksImported()){
            this.bookService.seedBooks();
        }
    }
}
