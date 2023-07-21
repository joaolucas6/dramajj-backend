package com.joaolucas.dramaJJ.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joaolucas.dramaJJ.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private long expiration;

    public String generateKey(User user){
        return JWT.
                create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }


}
