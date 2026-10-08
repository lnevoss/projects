package io.github.lnevoss.juice_shop.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class LoginResponse {
    private Long id;
    private String login;
    private String email;

    LoginResponse(Long id, String login, String email){
        this.id = id;
        this.login = login;
        this.email = email;
    }
}
