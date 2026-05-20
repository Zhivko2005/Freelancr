package com.freelance.freelance_api.controllers;

import com.freelance.freelance_api.dtos.AuthResponse;
import com.freelance.freelance_api.dtos.UserLoginDto;
import com.freelance.freelance_api.dtos.UserRegisterDto;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.data.web.ReactiveOffsetScrollPositionHandlerMethodArgumentResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userRegisterDto){
        User user = authService.register(userRegisterDto);
        return ResponseEntity.ok("User registered successfully: "+ user.getUsername());
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto){
        String token = authService.login(userLoginDto);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
