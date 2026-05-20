package com.freelance.freelance_api.services;

import com.freelance.freelance_api.dtos.RoleChangeDto;
import com.freelance.freelance_api.dtos.UserUpdateDto;
import com.freelance.freelance_api.entities.Role;
import com.freelance.freelance_api.entities.User;
import com.freelance.freelance_api.repositories.RoleRepository;
import com.freelance.freelance_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User updateUser(String usernameToUpdate, UserUpdateDto dto, String currentUsername){
        if(!usernameToUpdate.equals(currentUsername)){
            throw new RuntimeException("You are not authorized to update this user");
        }
        User user = userRepository.findByUsername(usernameToUpdate)
                .orElseThrow(() ->new RuntimeException("User not found with username: "+usernameToUpdate));
        if (dto.getEmail() != null && !dto.getEmail().isEmpty()){
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return userRepository.save(user);
    }
    @Transactional
    public void deleteUser(String usernameToDelete, String currentUsername, boolean isAdmin){
        if (!usernameToDelete.equals(currentUsername) && !isAdmin) {
            throw new RuntimeException("You are not authorized to delete this profile!");
        }

        User user = userRepository.findByUsername(usernameToDelete)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }
    @Transactional
    public User changeUserRole(String username, RoleChangeDto dto){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role newRole = roleRepository.findByName(dto.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + dto.getRoleName()));

        user.getRoles().clear();
        user.addRole(newRole);

        return userRepository.save(user);
    }
}
