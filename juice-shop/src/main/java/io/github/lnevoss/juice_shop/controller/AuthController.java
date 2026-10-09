package io.github.lnevoss.juice_shop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.lnevoss.juice_shop.dto.auth.LoginRequest;
import io.github.lnevoss.juice_shop.dto.auth.LoginResponse;
import io.github.lnevoss.juice_shop.dto.auth.RegisterRequest;
import io.github.lnevoss.juice_shop.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class AuthController {

    final AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(request));   
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.loginUser(request));
    }
}
