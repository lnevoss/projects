package io.github.lnevoss.juice_shop.exception;

import lombok.Getter;

@Getter 
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}