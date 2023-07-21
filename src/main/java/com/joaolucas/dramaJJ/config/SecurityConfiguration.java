package com.joaolucas.dramaJJ.config;

import com.joaolucas.dramaJJ.domain.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/api/v1/auth/**")
                            .permitAll()
                            .requestMatchers(
                                    HttpMethod.GET,
                                    "/api/v1/users/**",
                                    "/api/v1/actors/**",
                                    "/api/v1/dramas/**",
                                    "/api/v1/genres/**",
                                    "/api/v1/reviews/**"
                            )
                            .authenticated()
                            .requestMatchers(HttpMethod.POST,
                                    "/api/v1/reviews/**"
                            )
                            .authenticated()
                            .requestMatchers(HttpMethod.PUT,
                                    "/api/v1/reviews/**",
                                    "/api/v1/users/**"
                            )
                            .authenticated()
                            .requestMatchers(HttpMethod.DELETE,
                                    "/api/v1/users/**", "/api/v1/reviews/**"
                            )
                            .authenticated()
                            .anyRequest()
                            .hasRole(Role.ADMIN.name());

                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


}
