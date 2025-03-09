package bg.softuni.bookshop.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    boolean areImported();
}
