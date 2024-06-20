package com.aston.aston_project.entity.en;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_MANAGER("ROLE_MANAGER");

    private final String authority;

    RoleEnum(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

}
