package dev.aloys.jwt_oauth2.jwt_oauth2.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dev.aloys.jwt_oauth2.jwt_oauth2.Repo.userInfoRepo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class userInfoManagerConfig implements UserDetailsService{
    
    private final userInfoRepo userInfoRepo;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        return userInfoRepo.findByEmailId(emailId)
                            .map(userInfoConfig::new)
                            .orElseThrow(() -> new UsernameNotFoundException("User email not found: " + emailId));
    }

    

}