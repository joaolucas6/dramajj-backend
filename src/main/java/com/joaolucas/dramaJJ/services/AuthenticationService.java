package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.models.dto.AuthenticationRequest;
import com.joaolucas.dramaJJ.models.dto.AuthenticationResponse;
import com.joaolucas.dramaJJ.models.dto.RegisterRequest;
import com.joaolucas.dramaJJ.models.enums.Role;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import com.joaolucas.dramaJJ.utils.DataValidation;
import lombok.RequiredArgsConstructor;
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
        if(!DataValidation.isRegisterRequestValid(request)) throw new RuntimeException();

        User user = User
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
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
