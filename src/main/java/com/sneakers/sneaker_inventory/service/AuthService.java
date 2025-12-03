package com.sneakers.sneaker_inventory.service;

import com.sneakers.sneaker_inventory.dto.RegisterRequest;
import com.sneakers.sneaker_inventory.model.User;
import com.sneakers.sneaker_inventory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUserName())) {
            throw new RuntimeException("Username Already Exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email Already Exists");
        }

        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());

//        Encrypt password before saving
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptedPassword);

//        save to database
        return userRepository.save(user);
    }
}
