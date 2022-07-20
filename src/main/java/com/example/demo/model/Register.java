package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Register {

    @NotBlank
    @NonNull
    @Schema(description = "the email address of user ",example = "vincent@gmail.com")
    private String email;

    @NotBlank
    @NonNull
    @Schema(description = "the name about the user",example = "duncan su")
    private String name;

    @NotBlank
    @NonNull
    @Schema(description = "the password of users account",example = "123456")
    private String password;
}