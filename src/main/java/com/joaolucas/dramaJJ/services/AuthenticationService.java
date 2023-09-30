package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.exceptions.BadRequestException;
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
        if(!DataValidation.isRegisterRequestValid(request)) throw new BadRequestException("Invalid register info");

        User user = new User();

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());

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
