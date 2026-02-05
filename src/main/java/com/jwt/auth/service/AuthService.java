package com.jwt.auth.service;

import com.jwt.auth.dto.*;
import com.jwt.auth.entity.User;
import com.jwt.auth.repository.UserRepository;
import com.jwt.auth.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            System.out.println("[ERROR] Signup failed: Username already exists - " + request.getUsername());
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            System.out.println("[ERROR] Signup failed: Email already exists - " + request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);

        System.out.println("[AUTH] User signed up: " + user.getUsername());

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername(), "Signup successful");
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String username = auth.getName();
            System.out.println("[AUTH] User logged in: " + username);

            String token = jwtUtil.generateToken(username);
            return new AuthResponse(token, username, "Login successful");
        } catch (Exception e) {
            System.out.println("[ERROR] Login failed for user " + request.getUsername() + ": " + e.getMessage());
            throw new RuntimeException("Invalid username or password");
        }
    }

    public UserResponse getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
