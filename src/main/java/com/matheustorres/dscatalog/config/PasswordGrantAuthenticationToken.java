package com.matheustorres.dscatalog.config;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public class PasswordGrantAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private final String password;
    private final RegisteredClient registeredClient;

    public PasswordGrantAuthenticationToken(String username, String password,
            RegisteredClient registeredClient) {
        super(Collections.emptyList());
        this.username = username;
        this.password = password;
        this.registeredClient = registeredClient;
    }

    @Override
    public Object getCredentials() { return password; }

    @Override
    public Object getPrincipal() { return username; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public RegisteredClient getRegisteredClient() { return registeredClient; }
}
