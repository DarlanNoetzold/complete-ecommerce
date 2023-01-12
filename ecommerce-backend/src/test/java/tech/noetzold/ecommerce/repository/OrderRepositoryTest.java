package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.OrderItem;
import tech.noetzold.ecommerce.model.User;

import java.util.ArrayList;
import java.util.Date;
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
        Order orderToBeSaved = createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        Assertions.assertThat(orderSaved).isNotNull();
        Assertions.assertThat(orderSaved.getId()).isNotNull();
        Assertions.assertThat(orderSaved.getTotalPrice()).isEqualTo(orderToBeSaved.getTotalPrice());

    }

    @Test
    @DisplayName("Update the new Order")
    void save_UpdateOrder_WhenSuccessful(){
        Order orderToBeSaved = createOrder();

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
        Order orderToBeSaved = createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);

        this.orderRepository.delete(orderSaved);

        Optional<Order> orderOptional = this.orderRepository.findById(orderSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }


    private Order createOrder(){
        return Order.builder().orderItems(new ArrayList< OrderItem >()).user(new User()).createdDate(new Date()).id(1).sessionId("1").totalPrice(10.5).build();
    }
}