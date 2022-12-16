package com.newgo.activity.supermarketapp.config.security;
import com.newgo.activity.supermarketapp.domain.RoleName;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, JwtLoginAuthenticationFilter jwtLoginAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtLoginAuthenticationFilter = jwtLoginAuthenticationFilter;
    }

    @Bean
    @Primary
    @Profile("api")
    public SecurityFilterChain configureAPI(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()
                .antMatchers("/product/**")
                .hasAuthority(RoleName.ROLE_ADMINISTRATOR.toString())
                .antMatchers("/list/**")
                .hasAuthority(RoleName.ROLE_USER.toString())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtLoginAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Profile("thymeleaf")
    public SecurityFilterChain configureThymeleaf(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login-error")
            .defaultSuccessUrl("/success");

        http
                .authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority(RoleName.ROLE_ADMINISTRATOR.toString())
                .antMatchers("/user/**")
                .hasAuthority(RoleName.ROLE_USER.toString());

        return http.build();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
