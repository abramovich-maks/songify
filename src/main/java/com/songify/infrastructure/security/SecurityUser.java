package com.songify.infrastructure.security;

import com.songify.domain.usercrud.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities().stream()
                .map(authority -> (GrantedAuthority) () -> authority)
                .toList();
    }

    public List<String> getAuthoritiesAsString() {
        return user.getAuthorities().stream().toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public boolean isEnabled() {
        return user.isEnabled();
    }
}
