package com.freelance.freelance_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
