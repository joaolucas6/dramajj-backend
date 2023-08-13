package com.joaolucas.dramaJJ.models.dto;

import com.joaolucas.dramaJJ.models.enums.Role;

public record RegisterRequest(String firstName, String lastName, String username, String password, Role role){

}