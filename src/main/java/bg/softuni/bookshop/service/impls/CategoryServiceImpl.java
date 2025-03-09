package bg.softuni.bookshop.service.impls;

import bg.softuni.bookshop.data.entities.Category;
import bg.softuni.bookshop.data.repositories.CategoryRepository;
import bg.softuni.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORIES_PATH = "src/main/resources/files/categories.txt";

    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        Set<Category> categories = new HashSet<>();

        Files.readAllLines(Path.of(CATEGORIES_PATH))
                .forEach(line -> {
            Category category = new Category(line);
            categories.add(category);
        });

        this.categoryRepository.saveAllAndFlush(categories);
        System.out.printf("Count of imported Categories - %d", this.categoryRepository.count());
    }

    @Override
    public boolean areImported() {
        return this.categoryRepository.count() > 0;
    }
}
