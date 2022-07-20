package com.example.demo.controller;

import com.example.demo.lib.GoogleAuthenticator;
import com.example.demo.lib.JWTUtil;
import com.example.demo.lib.QRcodeUtil;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.FinalUser;
import com.example.demo.model.PasswordRam;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    FinalUser finalUser;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "get QRCode",description = "輸入帳號、密碼、驗證碼回傳QRCode圖形",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get QRCode successfully"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "驗證碼或帳號、密碼輸入錯誤",
                            content = @Content)
            })
    @PostMapping(value = "/")
    public ResponseEntity<Map<String,String>> getToken(HttpServletResponse response2,@RequestBody AuthRequest authRequest) {
        System.out.println(" authRequest :"+authRequest.getEmail());
        try {
            if(authRequest.getCaptcha().equals(passwordRam.getPassword())){
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
                FinalUser user = userRepository.findByEmail(authRequest.getEmail()).get();
                String secret = user.getSecret();
                String account = user.getName();
                userService.updatestatus(true,account);
                System.out.println(user.getStatus());
                String QRCode = QRcodeUtil.createGoogleAuthQRCodeData(secret,account,account);
                if(QRCode.equals(null)){
                    System.out.print("出錯了");
                }
                Map<String,String>response= Collections.singletonMap("QRcode",QRCode);
                QRcodeUtil.writeToStream(QRCode,response2.getOutputStream(),300,300);
                return ResponseEntity.ok(response);
            }
            else{
                Map<String,String>response= Collections.singletonMap("驗證碼:","輸入錯誤");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String,String>response1= Collections.singletonMap("登入狀態:","失敗，帳號或密碼錯誤");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response1);
        }
    }
    @Resource
    PasswordRam passwordRam;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /*--------------獲取驗證碼-------------*/
    @Operation(summary = "get captcha",description = "傳回五位數的數字驗證碼")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定義response輸出型別為image/jpeg
        response.setDateHeader("Expires", 0);
        // 設定http標準
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        // 設定請求頭
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 回應回傳的是image/jpeg型別
        response.setContentType("image/jpeg");


        /*--------------生成驗證碼-------------*/
        String text = defaultKaptcha.createText();// 獲取驗證碼文本內容

        System.out.println("驗證碼為" + text);
        //將password放入自訂義的變數裡面當暫存
        passwordRam.setPassword(text);
        // 將驗證碼文本內容放入session
        request.getSession().setAttribute("captcha", text);
        // 根據文本驗證碼內容創建圖形驗證碼
        BufferedImage image = defaultKaptcha.createImage(text);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 輸出流輸出檔案格式為jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Operation(summary = "get JWTToken",description = "輸入gmail 帳號及google Authenticator六位數驗證碼",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get JWTToken successfully"),
                    @ApiResponse(
                            responseCode = "401",
                            description = "登入時驗證碼出現錯誤或是沒有按照從頭登入的步驟來做",
                            content = @Content)
            })
    @RequestMapping(value = "/getjwttoken", method = {RequestMethod.POST})
    public ResponseEntity<Map<String,String>> returnJWTtoken(@RequestParam(name = "email") String email,@RequestParam(name = "code") Integer code) {
        Map<String,String>response1= Collections.singletonMap("result","登入碼驗證錯誤");
        if(email != null && email.trim().length() > 0){
            FinalUser user = userRepository.findByEmail(email).get();
            System.out.println("status="+user.getStatus());
            if(user != null&&user.getStatus()==true){
                String secret = user.getSecret();
                long timeMillis = System.currentTimeMillis();
                GoogleAuthenticator ga = new GoogleAuthenticator();
                ga.setWindowSize(0);
                boolean result = ga.check_code(secret, code, timeMillis);
                if(result){
                    String token = jwtUtil.Sign(user.getUuid().toString(),user.getEmail().toString());
                    Map<String,String> response = Collections.singletonMap("token",token);
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response1);
    }
}