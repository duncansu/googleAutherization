package com.example.demo.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class Register {

    @NotEmpty(message = "信箱輸入為空")
    @NonNull
    @Schema(description = "the email address of user ", example = "vincent@gmail.com")
    private String email;

    @NotEmpty(message = "名字輸入為空")
    @NonNull
    @Schema(description = "the name about the user", example = "duncan su")
    private String name;

    @NotEmpty(message = "密碼輸入為空")
    @NonNull
    @Schema(description = "the password of users account", example = "123456")
    private String password;
}