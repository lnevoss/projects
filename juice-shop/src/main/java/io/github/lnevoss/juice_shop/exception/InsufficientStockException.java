package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor 
public class InsufficientStockException extends Exception{
    private String msg;

    InsufficientStockException(String msg){
        super(msg);
        this.msg = msg;
    }
}