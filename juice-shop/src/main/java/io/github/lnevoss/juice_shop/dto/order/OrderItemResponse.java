package io.github.lnevoss.juice_shop.dto.order;

import java.math.BigDecimal;

import io.github.lnevoss.juice_shop.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
    private boolean success;

    OrderItemResponse(Long id, Long orderId, Product product, Integer quantity, BigDecimal unitPrice, boolean success){
        this.id = id;
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.success = success;
    }
}
