package com.example.demo.service;

import com.example.demo.lib.JWTUtil;
import com.example.demo.model.AuthRequest;
import com.example.demo.model.FinalUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<FinalUser> instance = userRepository.findByEmail(email);
        if (instance.isPresent()) {
            System.out.print("email"+email);
            System.out.print("instance"+instance);
            return instance.get();
        }
        throw new UsernameNotFoundException("email not found");
    }

}