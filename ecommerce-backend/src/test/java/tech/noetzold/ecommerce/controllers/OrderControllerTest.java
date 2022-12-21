package tech.noetzold.ecommerce.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import tech.noetzold.ecommerce.controller.OrderController;
import tech.noetzold.ecommerce.model.Order;

import java.util.List;

import static org.mockito.Mockito.*;

public class OrderControllerTest {

    private OrderController orderController = mock(OrderController.class);

    @Test
    public void ShouldReturnOrders(){
        when(orderController.getAllOrders("")).thenReturn(orderController.getAllOrders(""));
    }
}
