package com.example.demo.service;

import com.example.demo.model.FinalUser;
import com.example.demo.Request.Register;

import org.springframework.http.ResponseEntity;


import java.util.List;

public interface UserService {


    ResponseEntity<?> createUser(Register register);

    List<FinalUser> getAllUser();


}
