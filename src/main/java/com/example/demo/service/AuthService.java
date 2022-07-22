package com.example.demo.service;

import com.example.demo.model.FinalUser;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<FinalUser> instance = userRepository.findByEmail(email);
        if (instance.isPresent()) {
            System.out.print("email" + email);
            System.out.print("instance" + instance);
            return instance.get();
        }
        throw new UsernameNotFoundException("email not found");
    }

}