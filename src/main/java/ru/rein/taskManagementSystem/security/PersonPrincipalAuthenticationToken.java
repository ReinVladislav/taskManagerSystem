package ru.rein.taskManagementSystem.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class PersonPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final PersonPrincipal principal;

    public PersonPrincipalAuthenticationToken(PersonPrincipal principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public PersonPrincipal getPrincipal() {
        return principal;
    }
}
