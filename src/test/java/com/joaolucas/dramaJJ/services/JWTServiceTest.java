package com.joaolucas.dramaJJ.services;

import com.joaolucas.dramaJJ.models.entities.User;
import com.joaolucas.dramaJJ.models.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {

    private JWTService underTest;
    private User user;
    private String token;
    private String SECRET_KEY = "9AB9F3BC45EE57DEB9FC7111E1965";
    private final long expiration = 86400000;

    @BeforeEach
    void setUp() {
        underTest = new JWTService(SECRET_KEY, expiration);

        user = new User();

        user.setFirstName("jo");
        user.setLastName("jo");
        user.setUsername("jojo");
        user.setPassword("jojo");
        user.setRole(Role.ADMIN);

        token = "invalid token";
    }

    @Test
    void itShouldGenerateToken() {
        var result = underTest.generateToken(user);
        System.out.println(result);
        assertThat(result).isInstanceOf(String.class);
    }

    @Test
    void itShouldThrowExceptionForInvalidToken() {
        Assertions.assertThrows(RuntimeException.class, () -> underTest.extractUsernameAndValidate(token));
    }
}