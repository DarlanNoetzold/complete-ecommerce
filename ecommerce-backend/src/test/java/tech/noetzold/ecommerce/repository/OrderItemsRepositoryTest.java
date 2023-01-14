package tech.noetzold.ecommerce.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tech.noetzold.ecommerce.model.OrderItem;
import tech.noetzold.ecommerce.util.EcommerceCreator;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for OrderItem Repository")
@Log4j2
class OrderItemsRepositoryTest {
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Test
    @DisplayName("Persist the new OrderItem")
    void save_PersistOrder_WhenSuccessful(){
        OrderItem orderItemToBeSaved = EcommerceCreator.createListOrderItem().get(0);

        OrderItem orderItemSaved = this.orderItemsRepository.save(orderItemToBeSaved);

        Assertions.assertThat(orderItemSaved).isNotNull();
        Assertions.assertThat(orderItemSaved.getCreatedDate()).isNotNull();
        Assertions.assertThat(orderItemSaved.getOrder()).isEqualTo(orderItemToBeSaved.getOrder());

    }

    @Test
    @DisplayName("Update the new OrderItem")
    void save_UpdateOrder_WhenSuccessful(){
        OrderItem orderItemToBeSaved = EcommerceCreator.createListOrderItem().get(0);

        OrderItem orderItemSaved = this.orderItemsRepository.save(orderItemToBeSaved);

        orderItemSaved.setPrice(15.6);

        OrderItem orderItemUpdated = this.orderItemsRepository.save(orderItemSaved);
        Assertions.assertThat(orderItemSaved).isNotNull();
        Assertions.assertThat(orderItemSaved.getId()).isNotNull();
        Assertions.assertThat(orderItemUpdated.getPrice()).isEqualTo(orderItemSaved.getPrice());

    }

    @Test
    @DisplayName("Delete the new OrderItem")
    void save_DeleteOrder_WhenSuccessful(){
        OrderItem orderItemToBeSaved = EcommerceCreator.createListOrderItem().get(0);

        OrderItem orderItemSaved = this.orderItemsRepository.save(orderItemToBeSaved);

        this.orderItemsRepository.delete(orderItemSaved);

        Optional<OrderItem> orderOptional = this.orderItemsRepository.findById(orderItemSaved.getId());

        Assertions.assertThat(orderOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Id returns a OrderItem when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        OrderItem orderItemToBeSaved = EcommerceCreator.createListOrderItem().get(0);

        OrderItem orderItemSaved = this.orderItemsRepository.save(orderItemToBeSaved);

        Optional<OrderItem> orderOptional = this.orderItemsRepository.findById(orderItemSaved.getId());

        Assertions.assertThat(orderOptional)
                .isNotEmpty()
                .contains(orderItemSaved);

    }

    @Test
    @DisplayName("Find By id and return empty")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
        Optional<OrderItem> order = this.orderItemsRepository.findById(5);

        Assertions.assertThat(order).isEmpty();
    }

}