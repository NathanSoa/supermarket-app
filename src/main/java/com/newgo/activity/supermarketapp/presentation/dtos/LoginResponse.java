package com.newgo.activity.supermarketapp.presentation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String username;
    private String jwtToken;
}
