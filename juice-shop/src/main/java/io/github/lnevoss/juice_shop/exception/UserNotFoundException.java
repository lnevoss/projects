package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor 
public class UserNotFoundException extends Exception{
    private String msg;

    UserNotFoundException(String msg){
        super(msg);
        this.msg = msg;
    }
}