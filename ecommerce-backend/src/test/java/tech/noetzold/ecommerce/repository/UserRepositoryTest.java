package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DisplayName("Tests for User Repository")
@Log4j2
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Persist the new User")
    void save_PersistOrder_WhenSuccessful(){
        User userToBeSaved = EcommerceCreator.createUser();

        User userSaved = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userSaved.getEmail()).isEqualTo(userToBeSaved.getEmail());

    }

    @Test
    @DisplayName("Update the new User")
    void save_UpdateOrder_WhenSuccessful(){
        User userToBeSaved = EcommerceCreator.createUser();

        User userSaved = this.userRepository.save(userToBeSaved);

        userSaved.setFirstName("aaaaaaaaa");

        User userUpdated = this.userRepository.save(userSaved);
        Assertions.assertThat(userSaved).isNotNull();
        Assertions.assertThat(userSaved.getId()).isNotNull();
        Assertions.assertThat(userUpdated.getFirstName()).isEqualTo(userSaved.getFirstName());

    }

    @Test
    @DisplayName("Delete the new User")
    void save_DeleteOrder_WhenSuccessful(){
        User userToBeSaved = EcommerceCreator.createUser();

        User userSaved = this.userRepository.save(userToBeSaved);

        this.userRepository.delete(userSaved);

        Optional<User> orderOptional = this.userRepository.findById(userSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By email returns a User when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        User userToBeSaved = EcommerceCreator.createUser();

        User userSaved = this.userRepository.save(userToBeSaved);

        User userReturned = this.userRepository.findByEmail(userSaved.getEmail());

        Assertions.assertThat(userReturned).isEqualTo(userSaved);

    }

    @Test
    @DisplayName("Find User By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<User> order = this.userRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }
}