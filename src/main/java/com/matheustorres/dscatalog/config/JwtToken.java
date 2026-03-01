package com.matheustorres.dscatalog.config;

import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;

import com.matheustorres.dscatalog.entities.User;
import com.matheustorres.dscatalog.repositories.UserRepository;

@Component
public class JwtToken implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private final UserRepository repository;

    public JwtToken(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void customize(JwtEncodingContext context) {
        // Só customiza access tokens
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) return;

        String email = context.getPrincipal().getName();
        User user = repository.findByEmail(email);

        if (user != null) {
            context.getClaims()
                    .claim("userId", user.getId())
                    .claim("userFirstName", user.getFirstName());
        }
    }
}
