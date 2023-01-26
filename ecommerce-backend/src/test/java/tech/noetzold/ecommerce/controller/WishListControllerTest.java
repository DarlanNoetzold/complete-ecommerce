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
import tech.noetzold.ecommerce.dto.product.ProductDto;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.model.WishList;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.WishListService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class WishListControllerTest {

    @InjectMocks
    private WishListController wishListController;

    @Mock
    private WishListService wishListService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        User user = EcommerceCreator.createUser();
        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.any()))
                .thenReturn(user);
        BDDMockito.when(wishListService.readWishList(ArgumentMatchers.any()))
                .thenReturn(List.of(EcommerceCreator.createWishList(user)));
    }

    @Test
    void getWishList_WhenSuccessful() {
        ResponseEntity<List<ProductDto>> products = wishListController.getWishList("");

        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.getBody()).isNotEmpty();
        Assertions.assertThat(products.getBody().get(0).getName()).isEqualTo(EcommerceCreator.createProduct().getName());
        Assertions.assertThat(products.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void addWishList_WhenSuccessful() {
        ResponseEntity<ApiResponse> apiResponse = wishListController.addWishList(EcommerceCreator.createProduct(), "");

        Assertions.assertThat(apiResponse).isNotNull();
        Assertions.assertThat(apiResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
}