package io.propwave.tree.config.security;

import io.propwave.tree.auth.domain.Role;
import io.propwave.tree.auth.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(Role.values())
                .map(Enum::name)
                .toList()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getSocialInformation().getEmail();
    }

    @Override
    public String getUsername() {
        return user.getSocialInformation().getSocialId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
