package com.songify.infrastructure.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.songify.infrastructure.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
class JwtTokenGenerator {

    public static final String ROLES_CLAIM_NAME = "roles";

    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtConfigurationProperties properties;
    private final KeyPair keyPair;

    String authenticateAndGenerateToken(final String username, final String password) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken authenticate = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticate);
        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        Instant issuedAt = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        Instant expiresAt = issuedAt.plus(Duration.ofMinutes(properties.expirationMinutes()));

//        Algorithm algorithm = Algorithm.HMAC256(properties.secret());
        Algorithm algorithm = Algorithm.RSA256(null, (RSAPrivateCrtKey) keyPair.getPrivate());
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withIssuer(properties.issuer())
                .withClaim(ROLES_CLAIM_NAME, user.getAuthoritiesAsString())
                .sign(algorithm);
    }
}
