package com.example.demo.controller;

import com.example.demo.model.AuthRequest;
import com.example.demo.model.FinalUser;
import com.example.demo.model.Register;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserServiceImpl userServiceimple;
    @Autowired
    AuthService authService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "get all users",
            description = "透過token驗證後，給予所有的使用者資訊",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get user info successfully",
                            content = @Content(schema = @Schema(implementation = FinalUser.class))),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only authenticated user can get user info.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "404",
                            description = "The user doesn't exist.",
                            content = @Content)
            })
    @GetMapping(value = "/getall")
    public List<FinalUser> getAllUser() {
        return userService.getAllUser();
    }

    @Operation(summary = "get one user",description = "利用id 得到一個使用者的資訊",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get user info successfully"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only authenticated user can get user info.",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "404",
                            description = "The user doesn't exist.",
                            content = @Content)
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable(name="id") String id, Principal principal) {
        String userId = principal.getName();
        if (!id.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "get another user account is forbidden"));
        }
        return userService.getOneUser(id);
    }

    @Operation(summary = "create a user",description = "註冊api，創造一個新的使用者到DB",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "create user  successfully"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "bad request",
                            content = @Content)
            })
    @ResponseBody
    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(    @Parameter(schema = @Schema(implementation = AuthRequest.class))
                                            @RequestBody Register register) {
        return userService.createUser(register);
    }
    @Operation(summary = "delete a user ",description = "刪除一個使用者",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "delete user info successfully"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only authenticated user can delete user .",
                            content = @Content),
                    @ApiResponse(
                            responseCode = "404",
                            description = "The user doesn't exist.",
                            content = @Content)
            })
    @GetMapping (value="/delete")
    public ResponseEntity<Map<String,String>> DeleteByName(@RequestParam (name ="name") String name){
        if(userServiceimple.findByName(name).isEmpty()){
            Map<String,String>response1= Collections.singletonMap("刪除狀態:","失敗，名字不在DB裡面");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
        else {
            userServiceimple.deleteByName(name);
            Map<String,String>response1= Collections.singletonMap("刪除狀態:","成功");
            return ResponseEntity.status(HttpStatus.OK).body(response1);
        }
    }

    @Operation(summary = "update information",description = "更新一個使用者的資訊",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "update user info successfully"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only authenticated user can update user info.",
                            content = @Content),
            })
    @PostMapping(value = "/updateinformation")
    public ResponseEntity<Map<String,String>> UpdateTheInformation(@RequestParam (name="name")String name,

                                                                   @RequestParam(name="email") String email){
        if(userServiceimple.findByName(name).isEmpty()||userServiceimple.findByEmail(email).isEmpty()){
            Map<String,String>response1= Collections.singletonMap("更新狀態:","失敗，名字或email不在DB裡面");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
        else {
            userServiceimple.UpdateTheInformation(name, email);
            Map<String,String>response1= Collections.singletonMap("更新狀態:","成功");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response1);
        }
    }




}
