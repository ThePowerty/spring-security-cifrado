package com.williams.springsecuritycifrado.security.service;

import com.williams.springsecuritycifrado.entities.User;
import com.williams.springsecuritycifrado.exception.RegisterException;
import com.williams.springsecuritycifrado.security.jwt.JwtService;
import com.williams.springsecuritycifrado.repository.UserRepository;
import com.williams.springsecuritycifrado.security.dto.LoginRequest;
import com.williams.springsecuritycifrado.security.dto.RegisterRequest;
import com.williams.springsecuritycifrado.security.dto.JwtResponse;
import com.williams.springsecuritycifrado.security.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    JwtService jwtService;

    public JwtResponse login(LoginRequest authRequest) {

        // Autentificacion del usuario dentro de la base de datos
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );

        authenticationManager.authenticate(authToken);

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new JwtResponse(jwt);

    }

    public ResponseEntity<MessageResponse> register(RegisterRequest authRequest) {
        // Check 1: username
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            throw new RegisterException("Error: Username is already taken!");
        }

        // Check 2: email
        if (userRepository.existsByEmail(authRequest.getEmail())) {
            throw new RegisterException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(
                authRequest.getUsername(),
                authRequest.getEmail(),
                encoder.encode(authRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getUsername());
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());

        return extraClaims;
    }
}
