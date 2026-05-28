package com.webApp.blog.service.impl;

import com.webApp.blog.dto.request.LoginRequestDTO;
import com.webApp.blog.dto.request.SignupRequestDTO;
import com.webApp.blog.dto.response.AuthResponseDTO;
import com.webApp.blog.dto.response.UserResponseDTO;
import com.webApp.blog.exception.DuplicateResourceException;
import com.webApp.blog.exception.InvalidCredentialsException;
import com.webApp.blog.model.User;
import com.webApp.blog.repository.UserRepository;
import com.webApp.blog.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponseDTO signup(SignupRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(normalizeRole(request.getRole()));

        User savedUser = userRepository.save(user);
        return new AuthResponseDTO("Signup successful", UserResponseDTO.fromEntity(savedUser));
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        return new AuthResponseDTO("Login successful", UserResponseDTO.fromEntity(user));
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "POSTER";
        }
        return role.trim().toUpperCase();
    }
}
