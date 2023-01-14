package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.*;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Order Repository")
@Log4j2
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Persist the new Order")
    void save_PersistOrder_WhenSuccessful(){
        Order orderToBeSaved = EcommerceCreator.createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        Assertions.assertThat(orderSaved).isNotNull();
        Assertions.assertThat(orderSaved.getId()).isNotNull();
        Assertions.assertThat(orderSaved.getTotalPrice()).isEqualTo(orderToBeSaved.getTotalPrice());

    }

    @Test
    @DisplayName("Update the new Order")
    void save_UpdateOrder_WhenSuccessful(){
        Order orderToBeSaved = EcommerceCreator.createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        orderSaved.setTotalPrice(15.6);

        Order orderUpdated = this.orderRepository.save(orderSaved);
        Assertions.assertThat(orderSaved).isNotNull();
        Assertions.assertThat(orderSaved.getId()).isNotNull();
        Assertions.assertThat(orderUpdated.getTotalPrice()).isEqualTo(orderSaved.getTotalPrice());

    }

    @Test
    @DisplayName("Delete the new Order")
    void save_DeleteOrder_WhenSuccessful(){
        Order orderToBeSaved = EcommerceCreator.createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        this.orderRepository.delete(orderSaved);

        Optional<Order> orderOptional = this.orderRepository.findById(orderSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns a order when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Order orderToBeSaved = EcommerceCreator.createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        Optional<Order> orderOptional = this.orderRepository.findById(orderSaved.getId());

        Assertions.assertThat(orderOptional)
                .isNotEmpty()
                .contains(orderSaved);

    }

    @Test
    @DisplayName("Find By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<Order> order = this.orderRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }

}