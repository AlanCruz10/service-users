package org.app.serviceusers.management.users.infrastructure.configurations.security.user;

import lombok.AllArgsConstructor;
import org.app.serviceusers.management.users.infrastructure.adapters.ports.outputs.persistance.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.<GrantedAuthority>of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getCredential().getPassword();
    }

    @Override
    public String getUsername() {
        return user.getCredential().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public String getName() {
        return user.getUserProfile().getUsername();
    }

    public String getUuid() {
        return user.getUserProfile().getUuid();
    }

}