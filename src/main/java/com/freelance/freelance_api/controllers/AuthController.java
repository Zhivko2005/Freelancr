package com.freelance.freelance_api.controllers;

import com.freelance.freelance_api.dtos.UserLoginDto;
import com.freelance.freelance_api.dtos.UserRegisterDto;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.services.AuthService;
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
    public ResponseEntity<?> register(@RequestBody UserRegisterDto userRegisterDto){
       try{
           User user = authService.register(userRegisterDto);
           return ResponseEntity.ok(user);
       }catch (RuntimeException e){
           return ResponseEntity.badRequest().body(e.getMessage());
       }
    }

    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto){
        try {
            User user = authService.login(userLoginDto);
            return ResponseEntity.ok("Welcome " + user.getUsername());
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
