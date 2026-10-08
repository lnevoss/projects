package io.github.lnevoss.juice_shop.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class LoginRequest {
    @NotBlank
    private String login;
    @NotBlank @Size(min=5,max=72)
    private String password;

    LoginRequest(String login, String password){
        this.login = login;
        this.password = password;
    }
}
