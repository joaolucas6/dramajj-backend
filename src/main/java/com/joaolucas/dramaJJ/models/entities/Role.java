package com.joaolucas.dramaJJ.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

;

@AllArgsConstructor
public enum Role {
    USER(
            List.of(Permission.USER_READ)
    ),
    ADMIN(
            List.of(
                    Permission.ADMIN_CREATE,
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE
            )
    );

    @Getter
    private final List<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){

        List<SimpleGrantedAuthority> list = new ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .toList());

        list.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return list;
    }

}
