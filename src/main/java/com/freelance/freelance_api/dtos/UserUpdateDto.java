package com.freelance.freelance_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    @Email(message = "Please, provide a valid email address")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
