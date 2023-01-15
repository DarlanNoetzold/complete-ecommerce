package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.Cart;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;
@DataJpaTest
@DisplayName("Tests for Cart Repository")
@Log4j2
class CartsRepositoryTest {
    @Autowired
    private CartRepository cartsRepository;

    @Test
    @DisplayName("Persist the new Cart")
    void save_PersistOrder_WhenSuccessful() {
        Cart cartToBeSaved = EcommerceCreator.createCarts().get(0);

        Cart cartSaved = this.cartsRepository.save(cartToBeSaved);

        Assertions.assertThat(cartSaved).isNotNull();
        Assertions.assertThat(cartSaved.getCreatedDate()).isNotNull();
        Assertions.assertThat(cartSaved.getProduct()).isEqualTo(cartToBeSaved.getProduct());

    }

    @Test
    @DisplayName("Update the new Cart")
    void save_UpdateOrder_WhenSuccessful() {
        Cart cartToBeSaved = EcommerceCreator.createCarts().get(0);

        Cart cartSaved = this.cartsRepository.save(cartToBeSaved);

        cartSaved.setQuantity(15);

        Cart cartUpdated = this.cartsRepository.save(cartSaved);
        Assertions.assertThat(cartSaved).isNotNull();
        Assertions.assertThat(cartSaved.getId()).isNotNull();
        Assertions.assertThat(cartUpdated.getQuantity()).isEqualTo(cartSaved.getQuantity());

    }

    @Test
    @DisplayName("Delete the new Cart")
    void save_DeleteOrder_WhenSuccessful() {
        Cart cartToBeSaved = EcommerceCreator.createCarts().get(0);

        Cart cartSaved = this.cartsRepository.save(cartToBeSaved);

        this.cartsRepository.delete(cartSaved);

        Optional<Cart> orderOptional = this.cartsRepository.findById(cartSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns a Cart when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Cart cartToBeSaved = EcommerceCreator.createCarts().get(0);

        Cart cartSaved = this.cartsRepository.save(cartToBeSaved);

        Optional<Cart> orderOptional = this.cartsRepository.findById(cartSaved.getId());

        Assertions.assertThat(orderOptional)
                .isNotEmpty()
                .contains(cartSaved);

    }

    @Test
    @DisplayName("Find By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        Optional<Cart> order = this.cartsRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }
}