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
    private final Long id;



    public CustomUser(String email, String role, Long id) {
        this.email = email;
        this.role = role;
        this.id = id;
    }

    public static CustomUser of(String email,String role, Long id) {
        return new CustomUser(
                email,
                role,
                id
        );
    }
}
