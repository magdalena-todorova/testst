package bg.softuni.bookshop.service.impls;

import bg.softuni.bookshop.data.entities.Author;
import bg.softuni.bookshop.data.entities.Book;
import bg.softuni.bookshop.data.entities.Category;
import bg.softuni.bookshop.data.entities.enums.AgeRestriction;
import bg.softuni.bookshop.data.entities.enums.EditionType;
import bg.softuni.bookshop.data.repositories.AuthorRepository;
import bg.softuni.bookshop.data.repositories.BookRepository;
import bg.softuni.bookshop.data.repositories.CategoryRepository;
import bg.softuni.bookshop.service.BookService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_PATH = "src/main/resources/files/books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {

        Set<Book> books = new HashSet<>();

        Files.readAllLines(Path.of(BOOKS_PATH))
                .stream().filter(line -> !line.isEmpty())
                .forEach(line -> {
                    String[] tokens = line.split("\\s+");
                    Book book = new Book(
                            AgeRestriction.values()[Integer.parseInt(tokens[4])],
                            Integer.parseInt(tokens[2]),
                            EditionType.values()[Integer.parseInt(tokens[0])],
                            new BigDecimal(tokens[3]),
                            LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("d/M/yyyy")),
                            Arrays.stream(tokens).skip(5).collect(Collectors.joining(" "))
                    );

                    book.setAuthor(randomAuthor());
                    book.setCategories(randomCategories());
                    this.bookRepository.saveAndFlush(book);
                });
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new HashSet<>();
        long count = ThreadLocalRandom.current().nextLong(1, 4);


        for (int i = 0; i < count; i++) {
            long idCategory = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(idCategory).get());
        }

        return categories;
    }

    private Author randomAuthor() {
        long id = ThreadLocalRandom.current().nextLong(1, this.authorRepository.count() + 1);
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);
        return optionalAuthor.orElse(null);
    }

    @Override
    public boolean areBooksImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public void printAllBooksAfter2000() {
        this.bookRepository.findAllByReleaseDateAfter(LocalDate.of(2000, 1, 1))
                .forEach(book -> {
                    System.out.printf("%s%n", book.getTitle());
                });
    }

    @Override
    public void printAllBooksByGeorgePowell() {
        this.bookRepository.findBooksByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitleAsc(
                "George", "Powell").forEach(book ->
            System.out.printf("%s %s %s%n", book.getTitle(), book.getReleaseDate(), book.getCopies()));
    }
}
