package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class PasswordRam {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
