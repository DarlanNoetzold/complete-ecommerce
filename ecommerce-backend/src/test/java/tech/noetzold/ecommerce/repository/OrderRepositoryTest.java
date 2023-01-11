package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.OrderItem;
import tech.noetzold.ecommerce.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Order Repository")
@Log4j2
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Save the new Order")
    void save_PersistOrder_WhenSuccessful(){
        Order orderToBeSaved = createOrder();

        Order orderSaved = this.orderRepository.save(orderToBeSaved);
    }


    private Order createOrder(){
        return Order.builder().orderItems(new ArrayList< OrderItem >()).user(new User()).createdDate(new Date()).id(1).sessionId("1").totalPrice(10.5).build();
    }
}