package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.domain.dto.AuthenticationRequest;
import com.joaolucas.dramaJJ.domain.dto.AuthenticationResponse;
import com.joaolucas.dramaJJ.domain.dto.RegisterRequest;
import com.joaolucas.dramaJJ.domain.entities.Role;
import com.joaolucas.dramaJJ.domain.entities.User;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        User user = User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.valueOf(request.role()))
                .build();
        var token = jwtService.generateKey(userRepository.save(user));
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        );

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = jwtService.generateKey((User) auth.getPrincipal());

        return new AuthenticationResponse(token);
    }
}
