package com.joaolucas.dramaJJ.config;

import com.joaolucas.dramaJJ.services.CustomizedUserDetailsService;
import com.joaolucas.dramaJJ.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomizedUserDetailsService customizedUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHead = request.getHeader("Authorization");
        if(authHead == null || !authHead.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHead.substring(7);
        String username = jwtService.extractUsernameAndValidate(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customizedUserDetailsService.loadUserByUsername(username);

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );


            SecurityContextHolder.getContext().setAuthentication(authToken);


        }

        filterChain.doFilter(request, response);

    }
}
