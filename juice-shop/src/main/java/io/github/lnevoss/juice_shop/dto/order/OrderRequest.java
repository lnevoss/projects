package io.github.lnevoss.juice_shop.dto.order;

import java.util.ArrayList;
import java.util.List;

import io.github.lnevoss.juice_shop.model.OrderItem;
import io.github.lnevoss.juice_shop.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class OrderRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    OrderRequest(String customerName, String customerEmail, String customerPhone, List<OrderItem> items, OrderStatus status){
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.items = items;
        this.status = status;
    }
}
