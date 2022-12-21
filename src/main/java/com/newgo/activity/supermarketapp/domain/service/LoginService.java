package com.newgo.activity.supermarketapp.domain.service;

import com.newgo.activity.supermarketapp.presentation.dtos.LoginRequest;
import com.newgo.activity.supermarketapp.presentation.dtos.LoginResponse;
import com.newgo.activity.supermarketapp.utils.JwtTokenUtils;

import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtTokenUtils.generateToken(user.getUsername());
        LoginResponse response = new LoginResponse();
        response.setUsername(user.getUsername());
        response.setJwtToken(jwtToken);
        return response;
    }
}
