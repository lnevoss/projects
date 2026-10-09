package io.github.lnevoss.juice_shop.dto.product;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String category; 
    private String slug;
    private int stock;

    public ProductResponse(Long id, String name, String description, String imageUrl, BigDecimal price, String category, String slug, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
        this.slug = slug;
        this.stock = stock;
    }        
}
