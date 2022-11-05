package tech.noetzold.ecommerce.repository;

import tech.noetzold.ecommerce.model.Cart;
import tech.noetzold.ecommerce.model.User;
import tech.noetzold.ecommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    List<Cart> deleteByUser(User user);

}

