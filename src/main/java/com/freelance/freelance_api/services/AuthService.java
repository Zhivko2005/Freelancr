package com.freelance.freelance_api.services;

import com.freelance.freelance_api.dtos.UserLoginDto;
import com.freelance.freelance_api.dtos.UserRegisterDto;
import com.freelance.freelance_api.entities.Role;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.repositories.RoleRepository;
import com.freelance.freelance_api.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User register(UserRegisterDto userRegisterDto) {
        if (userRepository.findByUsername(userRegisterDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }else if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        } else if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(userRegisterDto.getPassword());
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Грешка: Ролята ROLE_USER не е намерена в базата!"));
        user.setRoles(Set.of(defaultRole));

        return userRepository.save(user);
    }
    public User login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}
