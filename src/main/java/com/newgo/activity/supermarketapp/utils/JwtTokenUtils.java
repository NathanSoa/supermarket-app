package com.newgo.activity.supermarketapp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtTokenUtils {

    @Value("${app.jwt.key}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private int expirationInMilliseconds;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String getUsername(String jwtTokenValue) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtTokenValue)
                .getBody()
                .getSubject();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
