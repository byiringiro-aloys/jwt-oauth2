package dev.aloys.jwt_oauth2.jwt_oauth2.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.aloys.jwt_oauth2.jwt_oauth2.Models.userInfoEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class userInfoConfig implements UserDetails {

    private final userInfoEntity userInfoEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays
            .stream(userInfoEntity.getRoles().split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();
    }

    @Override
    public String getPassword(){
        return userInfoEntity.getPassword();
    }

    @Override
    public String getUsername() {
       return userInfoEntity.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userInfoEntity.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userInfoEntity.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userInfoEntity.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userInfoEntity.isEnabled();
    }
    
}
