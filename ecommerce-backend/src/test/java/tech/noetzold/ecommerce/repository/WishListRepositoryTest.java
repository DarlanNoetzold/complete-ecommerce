package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.model.WishList;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;
@DataJpaTest
@DisplayName("Tests for WishList Repository")
@Log4j2
class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    @Test
    @DisplayName("Persist the new WishList")
    void save_PersistOrder_WhenSuccessful(){
        WishList wishListToBeSaved = EcommerceCreator.createWishList();

        WishList wishListSaved = this.wishListRepository.save(wishListToBeSaved);

        Assertions.assertThat(wishListSaved).isNotNull();
        Assertions.assertThat(wishListSaved.getId()).isNotNull();
        Assertions.assertThat(wishListSaved.getUser()).isEqualTo(wishListToBeSaved.getUser());

    }

    @Test
    @DisplayName("Update the new WishList")
    void save_UpdateOrder_WhenSuccessful(){
        WishList wishListToBeSaved = EcommerceCreator.createWishList();

        WishList wishListSaved = this.wishListRepository.save(wishListToBeSaved);
        User wishList = EcommerceCreator.createUser();
        wishListSaved.setUser(wishList);

        WishList wishListUpdated = this.wishListRepository.save(wishListSaved);
        Assertions.assertThat(wishListSaved).isNotNull();
        Assertions.assertThat(wishListSaved.getId()).isNotNull();
        Assertions.assertThat(wishListUpdated.getUser()).isEqualTo(wishListSaved.getUser());

    }

    @Test
    @DisplayName("Delete the new WishList")
    void save_DeleteOrder_WhenSuccessful(){
        WishList wishListToBeSaved = EcommerceCreator.createWishList();

        WishList wishListSaved = this.wishListRepository.save(wishListToBeSaved);

        this.wishListRepository.delete(wishListSaved);

        Optional<WishList> orderOptional = this.wishListRepository.findById(wishListSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By email returns a WishList when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        WishList wishListToBeSaved = EcommerceCreator.createWishList();

        WishList wishListSaved = this.wishListRepository.save(wishListToBeSaved);

        Optional<WishList> wishListReturned = this.wishListRepository.findById(wishListSaved.getId());

        Assertions.assertThat(wishListReturned.get()).isEqualTo(wishListSaved);

    }

    @Test
    @DisplayName("Find WishList By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<WishList> order = this.wishListRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }

}