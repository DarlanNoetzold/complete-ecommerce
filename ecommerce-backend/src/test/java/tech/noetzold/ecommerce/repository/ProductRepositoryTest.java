package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.Product;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Order Repository")
@Log4j2
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Persist the new Product")
    void save_PersistOrder_WhenSuccessful(){
        Product productToBeSaved = EcommerceCreator.createProduct();

        Product productSaved = this.productRepository.save(productToBeSaved);

        Assertions.assertThat(productSaved).isNotNull();
        Assertions.assertThat(productSaved.getId()).isNotNull();
        Assertions.assertThat(productSaved.getName()).isEqualTo(productToBeSaved.getName());

    }

    @Test
    @DisplayName("Update the new Product")
    void save_UpdateOrder_WhenSuccessful(){
        Product productToBeSaved = EcommerceCreator.createProduct();

        Product productSaved = this.productRepository.save(productToBeSaved);

        productSaved.setDescription("aaaaaaaaa");

        Product categoryUpdated = this.productRepository.save(productSaved);
        Assertions.assertThat(productSaved).isNotNull();
        Assertions.assertThat(productSaved.getId()).isNotNull();
        Assertions.assertThat(categoryUpdated.getDescription()).isEqualTo(productSaved.getDescription());

    }

    @Test
    @DisplayName("Delete the new Product")
    void save_DeleteOrder_WhenSuccessful(){
        Product productToBeSaved = EcommerceCreator.createProduct();

        Product productSaved = this.productRepository.save(productToBeSaved);

        this.productRepository.delete(productSaved);

        Optional<Product> orderOptional = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns a Product when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Product productToBeSaved = EcommerceCreator.createProduct();

        Product productSaved = this.productRepository.save(productToBeSaved);

        Optional<Product> orderOptional = this.productRepository.findById(productSaved.getId());

        Assertions.assertThat(orderOptional)
                .isNotEmpty()
                .contains(productSaved);

    }

    @Test
    @DisplayName("Find Product By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<Product> order = this.productRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }
}