package bg.softuni.bookshop.service.impls;

import bg.softuni.bookshop.data.entities.Author;
import bg.softuni.bookshop.data.repositories.AuthorRepository;
import bg.softuni.bookshop.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;

@Service
public class AuthorServiceImpl  implements AuthorService {
    private static final String AUTHORS_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        Files.readAllLines(Path.of(AUTHORS_PATH))
                        .stream()
                .filter(line-> !line.isEmpty())
                .forEach(line -> {
                    String[] split = line.split("\\s+");
                    Author author = new Author(split[0], split[1]);
                    authorRepository.saveAndFlush(author);
                });
        System.out.printf("Count of imported Authors - %d", this.authorRepository.count());
    }

    @Override
    public boolean areAuthorsImported() {
        return authorRepository.count() > 0;
    }

    @Override
    public void printAllAuthorsWith1BookBefore1990() {
        //this.authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
         //       .forEach(author -> {
           //         System.out.printf("%s %s %d%n", author.getFirstName(), author.getLastName(), author.getBooks().size());
           //     });
        this.authorRepository.findAll()
                .stream()
                .filter(author -> author.getBooks()
                                .stream()
                                .anyMatch(book -> book.getReleaseDate().isBefore(LocalDate.of(1990, 1, 1))))
                .forEach(author -> System.out.printf("%s %s%n",
                        author.getFirstName(), author.getLastName()));
    }

    @Override
    public void printAllAuthorsWithDescCount() {
        this.authorRepository.findAll()
                        .stream()
                .sorted((a,b)->b.getBooks().size()>a.getBooks().size() ? 1 : -1)
                .forEach(author -> {
            System.out.printf("%s %s - %d %n",
                    author.getFirstName(), author.getLastName(), author.getBooks().size());
        });
    }
}
