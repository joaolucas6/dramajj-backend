package com.joaolucas.dramaJJ.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joaolucas.dramaJJ.models.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private final String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private final long expiration;

    public String generateToken(User user){
        return JWT.
                create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public String extractUsernameAndValidate(String token){
            return JWT
                    .require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token)
                    .getSubject();
    }


}
