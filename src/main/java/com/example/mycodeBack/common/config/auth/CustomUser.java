package com.example.mycodeBack.common.config.auth;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class CustomUser{
    private final String email;
    private final String role;



    public CustomUser(String email, String role) {
        this.email = email;
        this.role = role;

    }

    public static CustomUser of(String email,String role) {
        return new CustomUser(
                email,
                role
        );
    }
}
