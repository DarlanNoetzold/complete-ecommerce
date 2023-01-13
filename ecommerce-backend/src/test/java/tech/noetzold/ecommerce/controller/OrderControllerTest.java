package tech.noetzold.ecommerce.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.repository.util.EcommerceCreator;
import tech.noetzold.ecommerce.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderServiceMock;

    @BeforeEach
    void setUp(){
        List<Order> orderPage = new ArrayList<>(List.of(EcommerceCreator.createOrder()));
        BDDMockito.when(orderServiceMock.listOrders(ArgumentMatchers.any()))
                .thenReturn(orderPage);
    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        User user = EcommerceCreator.createOrder().getUser();

        List<Order> orders = orderController.getAllOrders().getBody();

        Assertions.assertThat(orders).isNotNull();

        Assertions.assertThat(orders)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(orders.get(0).getUser()).isEqualTo(user);
    }
}