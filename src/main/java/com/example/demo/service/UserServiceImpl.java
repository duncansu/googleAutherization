package com.example.demo.service;

import com.example.demo.lib.GoogleAuthenticator;
import com.example.demo.model.FinalUser;
import com.example.demo.model.Register;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createUser(Register register) {
        UUID id = UUID.randomUUID();
        String password = register.getPassword();
        String secret = GoogleAuthenticator.generateSecretKey();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordDigest = passwordEncoder.encode(password);
        boolean status=false;

        FinalUser user = new FinalUser(id, register.getEmail(), passwordDigest,register.getName(),secret,status);
        userRepository.saveAndFlush(user);

        return ResponseEntity.ok(Map.of("userId", user.getUuid().toString()));
    }

    @Override
    public List<FinalUser> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getOneUser(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<FinalUser> instance = userRepository.findById(uuid);
        if (instance.isPresent()) {
            return ResponseEntity.ok(instance.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "not found record"));
    }
    public void deleteByName(String name){
        userRepository.deleteByName(name);
    }
    public List<FinalUser> UpdateTheInformation(String name,String email){
        return userRepository.UpdateTheInformation(name,email);
    }
    public Optional<FinalUser> findByName(String name){
        return userRepository.findByName(name);
    }
    public  Optional<FinalUser> findByEmail(String email){
        return userRepository.findByEmail(email);
    };
    public void updatestatus(boolean aa,String name){
        userRepository.updatestatus(aa,name);
    }

}
