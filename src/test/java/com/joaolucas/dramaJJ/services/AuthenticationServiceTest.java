package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.models.dto.AuthenticationRequest;
import com.joaolucas.dramaJJ.models.dto.AuthenticationResponse;
import com.joaolucas.dramaJJ.models.dto.RegisterRequest;
import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.models.enums.Role;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JWTService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    private AuthenticationService underTest;
    private User user;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(passwordEncoder, userRepository, jwtService, authenticationManager);
        user = new User();
        user.setUsername("jooj");
    }

    @Test
    void itShouldRegisterUser() {
        when(userRepository.save(user)).thenReturn(user);
        when(jwtService.generateKey(user)).thenReturn("token");

        RegisterRequest request = new RegisterRequest("jo", "jo", "jojo", "jojo", Role.ADMIN);

        var result = underTest.register(request);

        verify(userRepository, times(1)).save(user);

        assertThat(result).isInstanceOf(AuthenticationResponse.class);
    }

}