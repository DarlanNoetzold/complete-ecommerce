package tech.noetzold.ecommerce.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto implements Serializable {
    private Integer id;
    private @NotNull Integer productId;
    private @NotNull Integer quantity;


    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ",";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
