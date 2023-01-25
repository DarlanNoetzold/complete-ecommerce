package tech.noetzold.ecommerce.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.WishListService;

import static org.junit.jupiter.api.Assertions.*;

class WishListControllerTest {

    @InjectMocks
    private WishListService wishListService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getWishList_WhenSuccessful() {


    }

    @Test
    void addWishList_WhenSuccessful() {
    }
}