package com.seg.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()

                .requestMatchers(HttpMethod.GET, "/api/cities/**", "/api/countries/**", "/api/users/**", "/api/hotels/**", "/api/trips/**", "/api/variations/**", "/api/auth/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/**") //    /api/auth/register and /api/auth/authenticate
                .permitAll()

                .requestMatchers(HttpMethod.POST, "/api/users/**", "/api/trips/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/**", "/api/trips/**")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/**", "/api/trips/**")
                .hasAnyRole("USER", "ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cities/**", "/api/countries/**", "/api/hotels/**", "/api/variations/**")
                .hasRole("ADMIN")


//                .requestMatchers("/api/cities/**", "/api/countries/**", "/api/customers/**", "/api/hotels/**", "/api/trips/**", "/api/variations/**", "/api/auth/**")
//                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

