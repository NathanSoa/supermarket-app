package com.newgo.activity.supermarketapp.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return getRoleName().toString();
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
