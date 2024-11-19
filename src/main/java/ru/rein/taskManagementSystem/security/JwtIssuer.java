package ru.rein.taskManagementSystem.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(Request request) {
        var now = Instant.now();

        return JWT.create()
                .withSubject(String.valueOf(request.getPersonId()))
                .withIssuedAt(now)
                .withExpiresAt(now.plus(properties.getTokenDuration()))
                .withClaim("mail", request.getMail())
                .withClaim("au", request.getRoles())
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
    @Getter
    @Builder
    public static class Request {
        private final Long personId;
        private final String mail;
        private final List<String> roles;
    }
}
