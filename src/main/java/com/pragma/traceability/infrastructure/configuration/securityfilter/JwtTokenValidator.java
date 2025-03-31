package com.pragma.traceability.infrastructure.configuration.securityfilter;

import com.pragma.traceability.domain.spi.IJwtSecurityServicePort;
import com.pragma.traceability.infrastructure.helper.jwt.TokenHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final IJwtSecurityServicePort jwtSecurityService;

    public JwtTokenValidator(IJwtSecurityServicePort jwtSecurityService) {
        this.jwtSecurityService = jwtSecurityService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            if (jwtSecurityService.validateToken(token)) {
                String email = jwtSecurityService.getSubject(token);
                String stringAuthorities = jwtSecurityService.getClaim(token, "authorities");

                Collection<? extends GrantedAuthority> authorities =
                        AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                TokenHolder.setToken(token);
            }
        }
        filterChain.doFilter(request, response);
    }
}
