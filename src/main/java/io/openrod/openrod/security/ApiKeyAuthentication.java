package io.openrod.openrod.security;

import io.openrod.openrod.app.AppDTO;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;
    private final AppDTO app;

    public ApiKeyAuthentication(final String apiKey, final AppDTO app) {
        super(Collections.emptyList());
        this.apiKey = apiKey;
        this.app = app;

        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.apiKey;
    }

    @Override
    public AppDTO getPrincipal() {
        return this.app;
    }

}