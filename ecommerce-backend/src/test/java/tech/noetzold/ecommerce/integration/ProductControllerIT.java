package tech.noetzold.ecommerce.integration;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import tech.noetzold.ecommerce.dto.product.ProductDto;
import tech.noetzold.ecommerce.enums.Role;
import tech.noetzold.ecommerce.model.Product;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.repository.ProductRepository;
import tech.noetzold.ecommerce.repository.UserRepository;
import tech.noetzold.ecommerce.util.EcommerceCreator;
import org.assertj.core.api.Assertions;


import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    private static final User USER = User.builder()
            .password("{bcrypt}$2a$10$hSTIR1LEGbkA6US1B0IJVeoTsHrFKzPwXSeE40SvIFckopmMHoUTm")
            .firstName("darlan")
            .email("teste@mail.com")
            .role(Role.user)
            .build();

    private static final User ADMIN = User.builder()
            .password("{bcrypt}$2a$10$hSTIR1LEGbkA6US1B0IJVeoTsHrFKzPwXSeE40SvIFckopmMHoUTm")
            .firstName("darlan admin")
            .email("teste@mail.com")
            .role(Role.admin)
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("darlan", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("darlan admin", "academy");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }


    @Test
    @DisplayName("Integration test to list products")
    void list_ReturnListOfProducts_WhenSuccessful(){
        Product productSaved = productRepository.save(EcommerceCreator.createProduct());
        userRepository.save(USER);

        String expectedName = productSaved.getName();

        List<Product> products = testRestTemplateRoleUser.exchange("/product", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();

        Assertions.assertThat(products)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(products.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Integration test to add new products")
    void list_addNewProducts_WhenSuccessful(){
        userRepository.save(USER);

        ProductDto product = new ProductDto(EcommerceCreator.createProduct());

        ResponseEntity<Product> productResponseEntity = testRestTemplateRoleUser.postForEntity("/product/add", product, Product.class);

        Assertions.assertThat(productResponseEntity).isNotNull();
        Assertions.assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(productResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(productResponseEntity.getBody().getId()).isNotNull();
    }
}
