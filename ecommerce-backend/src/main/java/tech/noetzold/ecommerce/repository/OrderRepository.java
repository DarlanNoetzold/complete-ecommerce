package tech.noetzold.ecommerce.repository;

import tech.noetzold.ecommerce.model.Cart;
import tech.noetzold.ecommerce.model.Order;
import tech.noetzold.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedDateDesc(User user);

}
