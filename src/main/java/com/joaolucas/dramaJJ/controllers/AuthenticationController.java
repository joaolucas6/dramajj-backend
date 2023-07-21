package com.joaolucas.dramaJJ.controllers;

import com.joaolucas.dramaJJ.domain.dto.AuthenticationRequest;
import com.joaolucas.dramaJJ.domain.dto.AuthenticationResponse;
import com.joaolucas.dramaJJ.domain.dto.RegisterRequest;
import com.joaolucas.dramaJJ.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    )
    {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
            )
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}