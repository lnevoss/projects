package io.github.lnevoss.juice_shop.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor 
public class RegisterRequest {
    @NotBlank @Size(min=3,max=30)
    private String login;
    @Email @NotBlank 
    private String email;
    @NotBlank @Size(min=5,max=72)
    private String password;

    RegisterRequest(String login, String email, String password){
        this.login = login;
        this.email = email;
        this.password = password;
    }
}
