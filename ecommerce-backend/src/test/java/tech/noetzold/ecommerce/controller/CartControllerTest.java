package tech.noetzold.ecommerce.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import tech.noetzold.ecommerce.dto.cart.AddToCartDto;
import tech.noetzold.ecommerce.dto.cart.CartDto;
import tech.noetzold.ecommerce.dto.cart.CartItemDto;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.CartService;
import tech.noetzold.ecommerce.service.ProductService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;


    @BeforeEach
    void setUp() {
        User user = EcommerceCreator.createUser();
        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.any()))
                .thenReturn(user);

        BDDMockito.when(cartService.listCartItems(user))
                .thenReturn(new CartDto(List.of(new CartItemDto()), 0.0));

        BDDMockito.when(productService.getProductById(ArgumentMatchers.anyInt()))
                .thenReturn(EcommerceCreator.createProduct());
    }

    @Test
    void addToCart() {
        ResponseEntity<ApiResponse> response = cartController.addToCart(EcommerceCreator.createAddToCartDto(), "token");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void getCartItems() {
        ResponseEntity<CartDto> response = cartController.getCartItems("token");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateCartItem() {
        ResponseEntity<ApiResponse> response = cartController.updateCartItem(EcommerceCreator.createAddToCartDto(), "token");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteCartItem() {
        ResponseEntity<ApiResponse> response = cartController.deleteCartItem(1, "token");

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}