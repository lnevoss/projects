package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter  @NoArgsConstructor 
public class OrderNotFoundException extends Exception{
    private String msg;

    OrderNotFoundException(String msg){
        super(msg);
        this.msg = msg;
    }
}