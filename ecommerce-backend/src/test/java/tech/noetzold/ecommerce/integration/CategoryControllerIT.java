package tech.noetzold.ecommerce.integration;

import org.assertj.core.api.Assertions;
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
import tech.noetzold.ecommerce.dto.category.Category;
import tech.noetzold.ecommerce.enums.Role;
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.repository.Categoryrepository;
import tech.noetzold.ecommerce.repository.UserRepository;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryControllerIT {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private Categoryrepository categoryRepository;

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
    @DisplayName("Integration test to list categorys")
    void list_ReturnListOfCategorys_WhenSuccessful(){
        Category categorySaved = categoryRepository.save(EcommerceCreator.createCategory());
        userRepository.save(USER);

        String expectedName = categorySaved.getName();

        List<Category> categorys = testRestTemplateRoleUser.exchange("/category", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Category>>() {
                }).getBody();

        Assertions.assertThat(categorys)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(categorys.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Integration test to add new categorys")
    void list_addNewCategorys_WhenSuccessful(){
        userRepository.save(USER);

        Category category = EcommerceCreator.createCategory();
        category.getCategory().setId(1);
        Category categoryDto = new Category(category);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.postForEntity("/category/create", category.getCategory(), Category.class);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.postForEntity("/category/add", categoryDto, Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(categoryResponseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Integration test to update categorys")
    void list_updateCategorys_WhenSuccessful(){
        userRepository.save(USER);

        Category category = EcommerceCreator.createCategory();

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.postForEntity("/category/update/1", category, Category.class);

        Assertions.assertThat(categoryResponseEntity).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(categoryResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(categoryResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {
        Category savedCategory = categoryRepository.save(EcommerceCreator.createCategory());
        userRepository.save(USER);

        ResponseEntity<Category> categoryResponseEntity = testRestTemplateRoleUser.exchange("/category",
                HttpMethod.DELETE, null, Category.class, savedCategory.getId());

        Assertions.assertThat(categoryResponseEntity).isNotNull();

        Assertions.assertThat(categoryResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        userRepository.save(USER);

        List<Category> categorys = testRestTemplateRoleUser.exchange("/category", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Category>>() {
                }).getBody();

        Assertions.assertThat(categorys)
                .isNotNull()
                .isEmpty();

    }
}
