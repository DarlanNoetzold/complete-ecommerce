package tech.noetzold.ecommerce.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tech.noetzold.ecommerce.common.ApiResponse;
import tech.noetzold.ecommerce.config.message.RabbitmqConstantes;
import tech.noetzold.ecommerce.dto.product.ProductDto;
import tech.noetzold.ecommerce.model.Category;
import tech.noetzold.ecommerce.service.CategoryService;
import tech.noetzold.ecommerce.service.ProductService;

import java.util.Optional;


@Component
public class ProductConsumer {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RabbitListener(queues = RabbitmqConstantes.FILA_PRODUCT)
    private void consumidor(String mensagem) throws JsonProcessingException, InterruptedException {
        ProductDto productDto = new ObjectMapper().readValue(mensagem, ProductDto.class);
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return;
        }
        Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
    }
}
