package dev.aloys.jwt_oauth2.jwt_oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class webSecurityConfig {

    private final userInfoManagerConfig useriInfoManagerConfig;

    @Order(1)
    @Bean
    public SecurityFilterChain GeneralSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .securityMatcher("/", "/home", "/login", "/register", "/error", 
                           "/css/**", "/js/**", "/images/**", "/webjars/**",
                           "/favicon.ico", "/public/**", "/actuator/health")
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Order(2)
    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
            .securityMatcher("/api/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")
                .requestMatchers("/api/users/**/profile").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/reports/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )            
            .csrf(csrf->csrf.disable())
            .httpBasic(Customizer.withDefaults())
            .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/dashboard"))
            .logout(logout -> logout.logoutSuccessUrl("/login?logout"))
            .build();
    }

    @Order(3)
    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .securityMatcher("/dashboard/**","/settings/**","/user/**","/admin/**")
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasRole("USER")
            .requestMatchers("/settings/**").hasAnyRole("USER","ADMIN")
            .requestMatchers("/dashboard/**").hasAnyRole("USER","ADMIN")
            .anyRequest().authenticated()
        )
        .csrf(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Order(99)
    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .authorizeHttpRequests(auth->auth
            .anyRequest().authenticated()
        )
        .httpBasic(Customizer.withDefaults())
        .build();
    }
    
}
