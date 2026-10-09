package io.github.lnevoss.juice_shop.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class ProductRequest {
    private Long id;
    private String name;
    private String slug;

    public ProductRequest(Long id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
