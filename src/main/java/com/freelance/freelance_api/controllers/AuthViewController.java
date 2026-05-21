package com.freelance.freelance_api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class AuthViewController {
    @GetMapping("/login")
    public String displayLoginPage() {
        return "login";
    }
}
