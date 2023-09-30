package com.joaolucas.dramaJJ.services;


import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomizedUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomizedUserDetailsService underTest;

    private User user;

    @BeforeEach
    void setUp() {
        underTest = new CustomizedUserDetailsService(userRepository);
        user = new User();
        user.setUsername("Test");
    }

    @Test
    void itShouldLoadUserByUsername() {
        when(userRepository.findByUsername("Test")).thenReturn(Optional.of(user));

        var result = underTest.loadUserByUsername("Test");

        assertThat(result).isEqualTo(user);
    }
}