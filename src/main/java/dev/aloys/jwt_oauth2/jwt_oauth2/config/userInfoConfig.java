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
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }

    @Override
    public String getUsername() {
       throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return UserDetails.super.isEnabled();
    }
    
}
