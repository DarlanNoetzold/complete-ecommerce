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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.noetzold.ecommerce.dto.ResponseDto;
import tech.noetzold.ecommerce.dto.user.SignInResponseDto;
import tech.noetzold.ecommerce.enums.ResponseStatus;
import tech.noetzold.ecommerce.model.AuthenticationToken;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.repository.UserRepository;
import tech.noetzold.ecommerce.service.AuthenticationService;
import tech.noetzold.ecommerce.service.UserService;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.List;

import static tech.noetzold.ecommerce.config.MessageStrings.USER_CREATED;


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
        BDDMockito.when(userService.findAllUser())
                .thenReturn(List.of(EcommerceCreator.createUser()));

        BDDMockito.when(userService.signIn(ArgumentMatchers.any()))
                .thenReturn(new SignInResponseDto("success", EcommerceCreator.createAuthenticationToken().getToken()));

        BDDMockito.when(userService.signUp(ArgumentMatchers.any()))
                .thenReturn(new ResponseDto(ResponseStatus.success.toString(), USER_CREATED));

        BDDMockito.when(authenticationService.getUser(ArgumentMatchers.anyString()))
                .thenReturn(EcommerceCreator.createUser());

    }

    @Test
    @DisplayName("SignIn a user successful")
    void list_SignInUser_WhenSuccessful(){
        User user = EcommerceCreator.createUser();

        SignInResponseDto sign =  userController.Signin(EcommerceCreator.createSingIn(user.getEmail(),user.getPassword()));

        Assertions.assertThat(sign).isNotNull();

        Assertions.assertThat(sign.getStatus()).isEqualTo("success");

        Assertions.assertThat(sign.getToken()).isNotNull();
    }

    @Test
    @DisplayName("SingUp a user successful")
    void list_SingUp_WhenSuccessful(){
        ResponseDto signup =  userController.Signup(EcommerceCreator.createSingUp());

        Assertions.assertThat(signup).isNotNull();

        Assertions.assertThat(signup.getStatus()).isEqualTo("success");

        Assertions.assertThat(signup.getMessage()).isEqualTo(USER_CREATED);
    }

    @Test
    @DisplayName("Return all a user successful")
    void list_ReturnAllUser_WhenSuccessful(){
        List<User> users = userController.findAllUser("");

        Assertions.assertThat(users).isNotEmpty();
    }
}