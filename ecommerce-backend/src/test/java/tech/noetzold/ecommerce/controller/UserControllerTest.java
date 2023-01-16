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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.noetzold.ecommerce.dto.user.SignInResponseDto;
import tech.noetzold.ecommerce.model.AuthenticationToken;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.UserService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){
        User user = EcommerceCreator.createUser();
        AuthenticationToken token = EcommerceCreator.createAuthenticationToken();

        BDDMockito.when(userService.findAllUser())
                .thenReturn(List.of(EcommerceCreator.createUser()));

        SignInResponseDto response = new SignInResponseDto("success", token.getToken());

        BDDMockito.when(userService.signIn(EcommerceCreator.createSingIn(user.getEmail(),user.getPassword())))
                .thenReturn(response);

        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.anyString()))
                .thenReturn(EcommerceCreator.createUser());
    }

    @Test
    @DisplayName("SignIn a user successful")
    void list_SignInUser_WhenSuccessful(){
        
    }
}