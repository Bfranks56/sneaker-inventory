package com.sneakers.sneaker_inventory.controller;

import com.sneakers.sneaker_inventory.dto.AuthResponse;
import com.sneakers.sneaker_inventory.dto.LoginRequest;
import com.sneakers.sneaker_inventory.dto.RegisterRequest;
import com.sneakers.sneaker_inventory.model.User;
import com.sneakers.sneaker_inventory.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = authService.registerUser(request);
            AuthResponse response = new AuthResponse(
                    "User registered successfully",
                    user.getUsername()
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getUsername(), request.getPassword());

            // return token in response
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", request.getUsername());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
