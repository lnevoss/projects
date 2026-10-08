package io.github.lnevoss.juice_shop.dto.order;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import io.github.lnevoss.juice_shop.model.OrderItem;
import io.github.lnevoss.juice_shop.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class OrderResponse {
    private Long id;
    private Instant estimatedDeliveryDate;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    public OrderResponse(Long id, Instant estimatedDeliveryDate, String customerName, String customerEmail, String customerPhone, List<OrderItem> items, OrderStatus status) {
        this.id = id;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.items = items;
        this.status = status;
    }
}
