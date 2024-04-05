package com.williams.springsecuritycifrado.security.rest;

import com.williams.springsecuritycifrado.security.dto.LoginRequest;
import com.williams.springsecuritycifrado.security.dto.RegisterRequest;
import com.williams.springsecuritycifrado.security.dto.JwtResponse;
import com.williams.springsecuritycifrado.security.dto.MessageResponse;
import com.williams.springsecuritycifrado.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest authRequest){

        JwtResponse jwtDTO = authenticationService.login(authRequest);

        return ResponseEntity.ok(jwtDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(
            @RequestBody RegisterRequest authRequest) {

        MessageResponse messageResponse = authenticationService.register(authRequest).getBody();

        return ResponseEntity.ok(messageResponse);
    }
}
