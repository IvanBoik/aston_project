package com.aston.aston_project.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum RoleValue implements GrantedAuthority {
    ROLE_USER("ROLE_USER"), ROLE_MANAGER("ROLE_MANAGER");

    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }
}
