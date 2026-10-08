package io.github.lnevoss.juice_shop.service;

import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.lnevoss.juice_shop.dto.auth.RegisterRequest;
import io.github.lnevoss.juice_shop.model.User;
import io.github.lnevoss.juice_shop.repository.UserRepository;

@Service 
public class AuthService {
    final UserRepository userRepository;

    AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(RegisterRequest registerRequest){
        String hashedPassword;
        BcryptPassword4jPasswordEncoder encoder = new BcryptPassword4jPasswordEncoder();

        hashedPassword = encoder.encode(registerRequest.getPassword());

        User user = new User();
        user.setName(registerRequest.getLogin());
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(hashedPassword);

        userRepository.save(user);
        return userRepository.findById(user.getId()).isPresent();
    }

}
