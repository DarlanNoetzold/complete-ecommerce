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
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.CategoryService;
import tech.noetzold.ecommerce.service.WishListService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        User user = EcommerceCreator.createUser();
        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.any()))
                .thenReturn(user);

        BDDMockito.when(categoryService.listCategories())
                .thenReturn(List.of(EcommerceCreator.createCategory()));


    }

    @Test
    void getCategories() {
        ResponseEntity<List<Category>> categories = categoryController.getCategories();

        Assertions.assertThat(categories).isNotNull();
        Assertions.assertThat(categories.getBody()).isNotEmpty();
        Assertions.assertThat(categories.getBody().get(0).getCategoryName()).isEqualTo(EcommerceCreator.createCategory().getCategoryName());
        Assertions.assertThat(categories.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createCategory() {
        ResponseEntity<ApiResponse> categoryResponse = categoryController.createCategory(EcommerceCreator.createCategory());

        Assertions.assertThat(categoryResponse).isNotNull();

        Assertions.assertThat(categoryResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updateCategory() {
        ResponseEntity<ApiResponse> categoryResponse = categoryController.createCategory(EcommerceCreator.createCategory());

        ResponseEntity<ApiResponse> categoryResponseUpdate = categoryController.updateCategory(EcommerceCreator.createCategory().getId(), EcommerceCreator.createCategory());

        Assertions.assertThat(categoryResponseUpdate).isNotNull();

        Assertions.assertThat(categoryResponseUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}