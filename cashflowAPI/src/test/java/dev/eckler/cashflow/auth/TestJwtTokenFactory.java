package dev.eckler.cashflow.auth;

import org.springframework.security.oauth2.jwt.Jwt;

public final class TestJwtTokenFactory {

    public static Jwt forUsername(String username) {
        return Jwt.withTokenValue("test-token")
            .header("alg", "none")
            .claim("sub", username)
            .build();
    }

}
