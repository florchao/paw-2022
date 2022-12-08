package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;


    @Autowired
    private PawUserDetailsService pawUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private void basicAuthentication(String basicAuth) {
        int BASIC_TOKEN_LENGTH = 6;
        String basicToken = basicAuth.substring(BASIC_TOKEN_LENGTH);
        String decoded = new String(Base64.getDecoder().decode(basicToken));
        String[] credentials = decoded.split(":");
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials[0], credentials[1])
            );
        } catch (AuthenticationException e) {
            System.out.println("No estas autenticado hermano");
            throw new InsufficientAuthenticationException("Invalid credentials");
        }
        UserDetails userDetails = pawUserDetailsService.loadUserByUsername(credentials[0]);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) RequestContextHolder.getRequestAttributes()));
        if (auth != null && auth.isAuthenticated()) {
            createJWT(credentials);
        }
    }

    private void createJWT(String[] credentials) {
        System.out.println("aca en createJWT");
    }

    private void bearerAuthentication(String bearerAuth) {
        int BEARER_TOKEN_LENGTH = 7;
        String jwt = bearerAuth.substring(BEARER_TOKEN_LENGTH);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("aca en authenticateUser");
        String authHeaderContent = httpServletRequest.getHeader("Authorization");
        if (authHeaderContent.startsWith("Basic")) {
            basicAuthentication(authHeaderContent);
        } else if (authHeaderContent.startsWith("Bearer")) {
            bearerAuthentication(authHeaderContent);
        } else {
            System.out.println("No estas autenticado hermano");
            throw new IllegalArgumentException("Authentication not allowed");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}













