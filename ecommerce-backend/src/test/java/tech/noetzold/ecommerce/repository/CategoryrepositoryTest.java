package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.model.Product;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;
import java.util.Set;

@DataJpaTest
@DisplayName("Tests for Category Repository")
@Log4j2
class CategoryRepositoryTest {
    @Autowired
    private Categoryrepository categoryRepository;

    @Test
    @DisplayName("Persist the new Category")
    void save_PersistOrder_WhenSuccessful(){
        Category categoryToBeSaved = EcommerceCreator.createCategory();

        Category categorySaved = this.categoryRepository.save(categoryToBeSaved);

        Assertions.assertThat(categorySaved).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isNotNull();
        Assertions.assertThat(categorySaved.getCategoryName()).isEqualTo(categoryToBeSaved.getCategoryName());

    }

    @Test
    @DisplayName("Update the new Category")
    void save_UpdateOrder_WhenSuccessful(){
        Category categoryToBeSaved = EcommerceCreator.createCategory();

        Category categorySaved = this.categoryRepository.save(categoryToBeSaved);

        categorySaved.setDescription("aaaaaaaaa");

        Category categoryUpdated = this.categoryRepository.save(categorySaved);
        Assertions.assertThat(categorySaved).isNotNull();
        Assertions.assertThat(categorySaved.getId()).isNotNull();
        Assertions.assertThat(categoryUpdated.getDescription()).isEqualTo(categorySaved.getDescription());

    }

    @Test
    @DisplayName("Delete the new Category")
    void save_DeleteOrder_WhenSuccessful(){
        Category categoryToBeSaved = EcommerceCreator.createCategory();

        Category categorySaved = this.categoryRepository.save(categoryToBeSaved);

        this.categoryRepository.delete(categorySaved);

        Optional<Category> categoryOptional = this.categoryRepository.findById(categorySaved.getId());

        Assertions.assertThat(categoryOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns a Category when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Category categoryToBeSaved = EcommerceCreator.createCategory();

        Category categorySaved = this.categoryRepository.save(categoryToBeSaved);

        Optional<Category> categoryOptional = this.categoryRepository.findById(categorySaved.getId());

        Assertions.assertThat(categoryOptional)
                .isNotEmpty()
                .contains(categorySaved);

    }

    @Test
    @DisplayName("Find Category By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<Category> categoryOptional = this.categoryRepository.findById(5);

        Assertions.assertThat(categoryOptional).isEmpty();
    }
}