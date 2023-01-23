package tech.noetzold.ecommerce.util;

import tech.noetzold.ecommerce.dto.user.SignInDto;
import tech.noetzold.ecommerce.dto.user.SignupDto;
import tech.noetzold.ecommerce.enums.Role;
import tech.noetzold.ecommerce.model.*;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class EcommerceCreator {
    public static Order createOrder(){
        return Order.builder().orderItems(createListOrderItem()).user(createUser()).createdDate(new Date()).id(1).sessionId("1").totalPrice(10.5).build();
    }

    public static List<OrderItem> createListOrderItem(){
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(tech.noetzold.ecommerce.model.OrderItem.builder().price(3).product(createProduct()).quantity(2).createdDate(new Date()).build());
        orderItems.add(tech.noetzold.ecommerce.model.OrderItem.builder().price(5).product(createProduct()).quantity(3).createdDate(new Date()).build());
        return orderItems;
    }

    public static User createUser() {
        String myHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("123456".getBytes());
            byte[] digest = md.digest();
            myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return User.builder().orders(new ArrayList<>()).email("test@mail.com").role(Role.user).firstName("Test").lastName("Testing").password(myHash).build();
    }

    public static Product createProduct(){
         return Product.builder().price(10).carts(createCarts()).name("Product test").category(createCategory()).description("test").imageURL("www.google.com").build();
    }

    public static Category createCategory(){
        return Category.builder().categoryName("Category test").imageUrl("www.google.com").description("Testing").build();
    }

    public static List<Cart> createCarts(){
        List<Cart> carts = new ArrayList<>();
        User user = createUser();
        carts.add(Cart.builder().createdDate(new Date()).user(user).quantity(10).build());
        carts.add(Cart.builder().createdDate(new Date()).user(user).quantity(10).build());
        return carts;
    }

    public static SignupDto createSingUp(){
        return SignupDto.builder().email("teste@mail.com").firstName("teste").lastName("asdasd").password("123456").build();
    }

    public static SignInDto createSingIn(String email, String pass){
        return SignInDto.builder().email(email).password(pass).build();
    }

    public static WishList createWishList(){

        return WishList.builder().createdDate(new Date()).product(createProduct()).user(createUser()).build();
    }

    public static AuthenticationToken createAuthenticationToken(){
        return AuthenticationToken.builder().createdDate(new Date()).token("teste").user(createUser()).build();
    }
}
