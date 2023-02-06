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
import tech.noetzold.ecommerce.enums.Role;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.repository.UserRepository;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

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
    @DisplayName("Integration test to list users")
    void list_ReturnListOfUsers_WhenSuccessful(){
        User userSaved = userRepository.save(EcommerceCreator.createUser());
        userRepository.save(USER);

        String expectedEmail= userSaved.getEmail();

        List<User> users = testRestTemplateRoleUser.exchange("/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getEmail()).isEqualTo(expectedEmail);
    }

    @Test
    @DisplayName("Integration test to add new users")
    void list_addNewUsers_WhenSuccessful(){
        userRepository.save(USER);

        User user = EcommerceCreator.createUser();

        ResponseEntity<User> userResponseEntity = testRestTemplateRoleUser.postForEntity("/user/add", user, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
    }

    @Test
    @DisplayName("Integration test to update users")
    void list_updateUsers_WhenSuccessful(){
        userRepository.save(USER);

        User user = EcommerceCreator.createUser();

        ResponseEntity<User> userResponseEntity = testRestTemplateRoleUser.postForEntity("/user/update/1", user, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {
        User savedUser = userRepository.save(EcommerceCreator.createUser());
        userRepository.save(USER);

        ResponseEntity<User> userResponseEntity = testRestTemplateRoleUser.exchange("/user",
                HttpMethod.DELETE, null, User.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("findByName returns an empty list of user when user is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        userRepository.save(USER);

        List<User> users = testRestTemplateRoleUser.exchange("/user", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();

    }
}
