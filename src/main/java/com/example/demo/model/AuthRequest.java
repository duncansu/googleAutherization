package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotBlank
    @Schema(description = "the email address of user ",example = "vincent@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "the password of users account",example = "123456")
    private String password;
    @NotBlank
    @Schema(description = "the google captcha google give",example="xxxxx")
    private String captcha;
}