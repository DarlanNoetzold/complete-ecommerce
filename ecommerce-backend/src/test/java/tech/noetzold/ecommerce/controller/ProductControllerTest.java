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
import tech.noetzold.ecommerce.dto.product.ProductDto;
import tech.noetzold.ecommerce.service.CategoryService;
import tech.noetzold.ecommerce.service.ProductService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;


@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp(){

        BDDMockito.when(productService.getProductById(ArgumentMatchers.any()))
                .thenReturn(EcommerceCreator.createProduct());

        BDDMockito.when(productService.listProducts())
                .thenReturn(List.of(new ProductDto(EcommerceCreator.createProduct())));

    }

    @Test
    @DisplayName("List of product when successful")
    void list_ReturnsListOfProducts_WhenSuccessful(){
        ResponseEntity<List<ProductDto>> products = productController.getProducts();

        Assertions.assertThat(products).isNotNull();

        Assertions.assertThat(products.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}