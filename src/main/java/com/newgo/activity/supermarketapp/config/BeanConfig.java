package com.newgo.activity.supermarketapp.config;

import com.newgo.activity.supermarketapp.domain.Product;
import com.newgo.activity.supermarketapp.entities.dto.ProductDTO;

import com.newgo.activity.supermarketapp.utils.JwtTokenUtils;
import org.apache.commons.lang3.ArrayUtils;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Base64;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Byte[], String> byteCodeToString64 = context -> Base64.getEncoder().encodeToString(ArrayUtils.toPrimitive(context.getSource()));

        modelMapper.typeMap(Product.class, ProductDTO.class)
                .addMappings(src -> {
                    src.using(byteCodeToString64);
                    src.map(Product::getPhoto, ProductDTO::setPhoto);
                });

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtils jwtTokenUtils() {
        return new JwtTokenUtils();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
