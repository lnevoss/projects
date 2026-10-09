package io.github.lnevoss.juice_shop.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.lnevoss.juice_shop.dto.auth.LoginRequest;
import io.github.lnevoss.juice_shop.dto.auth.LoginResponse;
import io.github.lnevoss.juice_shop.dto.auth.RegisterRequest;
import io.github.lnevoss.juice_shop.exception.UserNotFoundException;
import io.github.lnevoss.juice_shop.model.User;
import io.github.lnevoss.juice_shop.repository.UserRepository;

@Service 
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    final UserRepository userRepository;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse registerUser(RegisterRequest registerRequest){
        String hashedPassword;
        LoginResponse response = new LoginResponse();
        PasswordEncoder encoder = passwordEncoder;

        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("Email is already in use.");
        }

        hashedPassword = encoder.encode(registerRequest.getPassword());

        User user = new User();
        user.setName(registerRequest.getLogin());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);

        response.setId(user.getId());
        response.setLogin(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

    public LoginResponse loginUser(LoginRequest loginRequest){
        LoginResponse response = new LoginResponse();
        PasswordEncoder encoder = passwordEncoder;
        User user;

        response.setId((long) 0);
        response.setLogin("");
        response.setEmail("");

        try {
            user = userRepository
                .findByEmail(loginRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException());
                        
            if(encoder.matches(loginRequest.getPassword(), user.getPasswordHash())){
                response.setId(user.getId());
                response.setLogin(user.getName());
                response.setEmail(user.getEmail());
            } else{
                throw new BadCredentialsException("Incorrect Password.");
            }
        } catch (UserNotFoundException e) {
            throw new BadCredentialsException("Incorrect Password.");        
        }

        return response;
    }
}
