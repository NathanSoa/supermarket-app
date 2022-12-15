package com.newgo.activity.supermarketapp.config.security;

import com.newgo.activity.supermarketapp.utils.JwtTokenUtils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtLoginAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;

    public JwtLoginAuthenticationFilter(JwtTokenUtils jwtTokenUtils, UserDetailsService userDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationToken = request.getHeader("Authorization");

        if(tokenIsInvalid(authorizationToken)){
            chain.doFilter(request, response);
            return;
        }

        String jwtTokenValue = authorizationToken.split(" ")[1];
        String username = jwtTokenUtils.getUsername(jwtTokenValue);

        UserDetails user = userDetailsService.loadUserByUsername(username);

        setSpringAuthenticationContext(request, user);

        chain.doFilter(request, response);
    }

    private boolean tokenIsInvalid(String authorizationToken) {
        return Objects.isNull(authorizationToken) || !authorizationToken.startsWith("Bearer");
    }

    private void setSpringAuthenticationContext(HttpServletRequest request, UserDetails user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
