package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
        super("Product not found.");
    }
}