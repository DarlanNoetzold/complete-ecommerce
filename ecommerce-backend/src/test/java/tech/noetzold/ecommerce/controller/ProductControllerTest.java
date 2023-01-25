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
import tech.noetzold.ecommerce.dto.product.ProductDto;
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.model.Product;
import tech.noetzold.ecommerce.service.CategoryService;
import tech.noetzold.ecommerce.service.ProductService;
import tech.noetzold.ecommerce.service.RabbitmqService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    RabbitmqService rabbitmqService;

    @BeforeEach
    void setUp(){
        Optional<Category> optionalCategory = Optional.of(EcommerceCreator.createCategory());

        BDDMockito.when(productService.getProductById(ArgumentMatchers.any()))
                .thenReturn(EcommerceCreator.createProduct());

        BDDMockito.when(productService.listProducts())
                .thenReturn(List.of(new ProductDto(EcommerceCreator.createProduct())));

        BDDMockito.when(categoryService.readCategory(ArgumentMatchers.anyInt()))
                .thenReturn(optionalCategory);

    }

    @Test
    @DisplayName("List of product when successful")
    void list_ReturnsListOfProducts_WhenSuccessful(){
        ResponseEntity<List<ProductDto>> products = productController.getProducts();

        Assertions.assertThat(products).isNotNull();

        Assertions.assertThat(products.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Add a product when successful")
    void addProducts_WhenSuccessful(){
        ResponseEntity<ApiResponse> productSaved = productController.addProduct(new ProductDto(EcommerceCreator.createProduct()));

        Assertions.assertThat(productSaved).isNotNull();

        Assertions.assertThat(productSaved.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Update a product when successful")
    void updateProducts_WhenSuccessful(){
        ProductDto product = new ProductDto(EcommerceCreator.createProduct());
        ResponseEntity<ApiResponse> productSaved = productController.addProduct(product);

        ResponseEntity<ApiResponse> productUpdate = productController.updateProduct(product.getId(), product);

        Assertions.assertThat(productUpdate).isNotNull();

        Assertions.assertThat(productUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
    }



}