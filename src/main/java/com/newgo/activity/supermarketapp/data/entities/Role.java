package com.newgo.activity.supermarketapp.data.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    @ToString.Include
    @NotNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    @Override
    public String getAuthority() {
        return getRoleName().toString();
    }
}
