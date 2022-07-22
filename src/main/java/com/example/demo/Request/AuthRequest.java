package com.example.demo.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "信箱輸入為空")
    @Schema(description = "the email address of user ", example = "vincent@gmail.com")
    private String email;
    @NotEmpty(message = "密碼輸入為空")
    @Schema(description = "the password of users account", example = "123456")
    private String password;
    @NotEmpty(message = "驗證碼輸入為空")
    @Schema(description = "the google captcha google give", example = "xxxxx")
    private String captcha;
}