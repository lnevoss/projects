package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor 
public class ProductNotFoundException extends Exception{
    private String msg;

    ProductNotFoundException(String msg){
        super(msg);
        this.msg = msg;
    }
}