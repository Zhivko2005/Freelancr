package com.freelance.freelance_api.controllers;

import com.freelance.freelance_api.dtos.AuthResponse;
import com.freelance.freelance_api.dtos.RoleChangeDto;
import com.freelance.freelance_api.dtos.UserUpdateDto;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable String username,
                                           @Valid @RequestBody UserUpdateDto dto,
                                           Authentication authentication) {
        User updated = userService.updateUser(username, dto, authentication.getName());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        userService.deleteUser(username, authentication.getName(), isAdmin);
        return ResponseEntity.ok("User profile deleted successfully");
    }
    @PutMapping("/{username}/role")
    public ResponseEntity<User> changeUserRole(@PathVariable String username,
                                               @Valid @RequestBody RoleChangeDto dto) {
        User updatedUser = userService.changeUserRole(username, dto);
        return ResponseEntity.ok(updatedUser);
    }
}
