package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.model.AuthenticationToken;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for AuthenticationToken Repository")
@Log4j2
class TokenRepositoryTest {
    @Autowired
    private TokenRepository authenticationTokenRepository;

    @Test
    @DisplayName("Persist the new AuthenticationToken")
    void save_PersistOrder_WhenSuccessful(){
        AuthenticationToken authenticationTokenToBeSaved = EcommerceCreator.createAuthenticationToken();

        AuthenticationToken authenticationTokenSaved = this.authenticationTokenRepository.save(authenticationTokenToBeSaved);

        Assertions.assertThat(authenticationTokenSaved).isNotNull();
        Assertions.assertThat(authenticationTokenSaved.getId()).isNotNull();
        Assertions.assertThat(authenticationTokenSaved.getUser()).isEqualTo(authenticationTokenToBeSaved.getUser());

    }

    @Test
    @DisplayName("Update the new AuthenticationToken")
    void save_UpdateOrder_WhenSuccessful(){
        AuthenticationToken authenticationTokenToBeSaved = EcommerceCreator.createAuthenticationToken();

        AuthenticationToken authenticationTokenSaved = this.authenticationTokenRepository.save(authenticationTokenToBeSaved);
        User authenticationToken = EcommerceCreator.createUser();
        authenticationTokenSaved.setUser(authenticationToken);

        AuthenticationToken authenticationTokenUpdated = this.authenticationTokenRepository.save(authenticationTokenSaved);
        Assertions.assertThat(authenticationTokenSaved).isNotNull();
        Assertions.assertThat(authenticationTokenSaved.getId()).isNotNull();
        Assertions.assertThat(authenticationTokenUpdated.getUser()).isEqualTo(authenticationTokenSaved.getUser());

    }

    @Test
    @DisplayName("Delete the new AuthenticationToken")
    void save_DeleteOrder_WhenSuccessful(){
        AuthenticationToken authenticationTokenToBeSaved = EcommerceCreator.createAuthenticationToken();

        AuthenticationToken authenticationTokenSaved = this.authenticationTokenRepository.save(authenticationTokenToBeSaved);

        this.authenticationTokenRepository.delete(authenticationTokenSaved);

        Optional<AuthenticationToken> orderOptional = this.authenticationTokenRepository.findById(authenticationTokenSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By email returns a AuthenticationToken when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        AuthenticationToken authenticationTokenToBeSaved = EcommerceCreator.createAuthenticationToken();

        AuthenticationToken authenticationTokenSaved = this.authenticationTokenRepository.save(authenticationTokenToBeSaved);

        Optional<AuthenticationToken> authenticationTokenReturned = this.authenticationTokenRepository.findById(authenticationTokenSaved.getId());

        Assertions.assertThat(authenticationTokenReturned.get()).isEqualTo(authenticationTokenSaved);

    }

    @Test
    @DisplayName("Find AuthenticationToken By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<AuthenticationToken> order = this.authenticationTokenRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }
}