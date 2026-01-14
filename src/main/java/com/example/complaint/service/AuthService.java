package com.example.complaint.service;

import com.example.complaint.dto.AuthResponse;
import com.example.complaint.dto.LoginRequest;
import com.example.complaint.dto.RegisterRequest;
import com.example.complaint.entity.User;
import com.example.complaint.repository.UserRepository;
import com.example.complaint.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public AuthResponse register(RegisterRequest request) {
        //Validations
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        
        User savedUser = userRepository.save(user);

        String token = jwtTokenProvider.generateToken(savedUser.getUsername());
        
        return new AuthResponse(token, savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole());
    }
    
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtTokenProvider.generateToken(authentication);
        
        return new AuthResponse(token, user.getUsername(), user.getEmail(), user.getRole());
    }
}