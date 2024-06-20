package com.aston.aston_project.util;

import com.aston.aston_project.entity.en.RoleEnum;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private String email;
    private String password;
    private RoleEnum role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
