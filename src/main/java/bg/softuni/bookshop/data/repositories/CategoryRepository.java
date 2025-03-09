package bg.softuni.bookshop.data.repositories;

import bg.softuni.bookshop.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


}
