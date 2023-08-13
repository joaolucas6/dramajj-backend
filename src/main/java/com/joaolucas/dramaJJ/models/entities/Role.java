package com.joaolucas.dramaJJ.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

;

@AllArgsConstructor
public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    @Getter
    private final String name;

    public List<SimpleGrantedAuthority> getAuthorities(){

        List<SimpleGrantedAuthority> list = new ArrayList<>();

        list.add(new SimpleGrantedAuthority("ROLE_" + getName()));

        return list;
    }

}
