package tech.noetzold.ecommerce.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
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
import tech.noetzold.ecommerce.dto.checkout.StripeResponse;
import tech.noetzold.ecommerce.dto.user.UserCreateDto;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.User;
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

        BDDMockito.when(orderServiceMock.getOrder(ArgumentMatchers.any()))
                .thenReturn(EcommerceCreator.createOrder());

        try {
            BDDMockito.when(orderServiceMock.createSession(ArgumentMatchers.any()))
                    .thenReturn(new Session());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        BDDMockito.when(userService.findAllUser())
                .thenReturn(List.of(EcommerceCreator.createUser()));

        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.anyString()))
                .thenReturn(EcommerceCreator.createUser());
    }

    @Test
    @DisplayName("List of order when successful")
    void list_ReturnsListOfOrders_WhenSuccessful(){
        User user = EcommerceCreator.createUser();
        userService.createUser("", new UserCreateDto(EcommerceCreator.createUser()));
        List<Order> orders = orderController.getAllOrders("").getBody();

        Assertions.assertThat(orders).isNotNull();

        Assertions.assertThat(orders)
                .isNotEmpty()
                .hasSize(1);
        orders.get(0).setUser(user);
        Assertions.assertThat(orders.get(0).getUser().getEmail()).isEqualTo(userService.findAllUser().get(0).getEmail());
    }

    @Test
    @DisplayName("Place a new order")
    void list_PlaceNewOrders_WhenSuccessful(){

        ResponseEntity<ApiResponse> order = orderController.placeOrder("", "");

        Assertions.assertThat(order).isNotNull();

        Assertions.assertThat(order.getStatusCode()).isEqualTo(HttpStatus.CREATED);


    }

    @Test
    @DisplayName("Get order by id")
    void list_GetOrderById_WhenSuccessful(){

        ResponseEntity<Object> order = orderController.getOrderById(1, "");

        Assertions.assertThat(order).isNotNull();

        Assertions.assertThat(order.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("Do the checkout list")
    void list_CheckoutList_WhenSuccessful(){

        ResponseEntity<StripeResponse> order = null;
        try {
            order = orderController.checkoutList(new ArrayList<>());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThat(order).isNotNull();

        Assertions.assertThat(order.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}