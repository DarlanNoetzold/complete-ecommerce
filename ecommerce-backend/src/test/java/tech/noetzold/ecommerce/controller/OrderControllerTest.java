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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.noetzold.ecommerce.common.ApiResponse;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.util.EcommerceCreator;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.OrderService;
import tech.noetzold.ecommerce.service.UserService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @InjectMocks
    private UserController userController;
    @Mock
    private OrderService orderServiceMock;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){
        List<Order> orderPage = new ArrayList<>(List.of(EcommerceCreator.createOrder()));
        BDDMockito.when(orderServiceMock.listOrders(ArgumentMatchers.any()))
                .thenReturn(orderPage);

        BDDMockito.when(userService.findAllUser())
                .thenReturn(List.of(EcommerceCreator.createUser()));

        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.anyString()))
                .thenReturn(EcommerceCreator.createUser());
    }

    @Test
    @DisplayName("List of order when successful")
    void list_ReturnsListOfOrders_WhenSuccessful(){

        List<Order> orders = orderController.getAllOrders("").getBody();

        Assertions.assertThat(orders).isNotNull();

        Assertions.assertThat(orders)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(orders.get(0).getUser().getEmail()).isEqualTo(userService.findAllUser().get(0).getEmail());
    }

    @Test
    @DisplayName("Place a new order")
    void list_PlaceNewOrders_WhenSuccessful(){

        ResponseEntity<ApiResponse> order = orderController.placeOrder("", "");

        Assertions.assertThat(order).isNotNull();

        Assertions.assertThat(order.getStatusCode()).isEqualTo(HttpStatus.CREATED);


    }
}