package com.newgo.activity.supermarketapp.presentation.controller.api;

import com.newgo.activity.supermarketapp.presentation.dtos.LoginRequest;
import com.newgo.activity.supermarketapp.presentation.dtos.LoginResponse;
import com.newgo.activity.supermarketapp.utils.JwtTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {

    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public LoginController(JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@Valid @RequestBody LoginRequest request) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtTokenUtils.generateToken(user.getUsername());
            LoginResponse response = new LoginResponse();
            response.setUsername(user.getUsername());
            response.setJwtToken(jwtToken);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
