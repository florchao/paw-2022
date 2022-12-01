package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Provider
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    private void basicAuthentication(String basicAuth) {
        int BASIC_TOKEN_LENGTH = 6;
        String basicToken = basicAuth.substring(BASIC_TOKEN_LENGTH);
        String decoded = new String(Base64.getDecoder().decode(basicToken));
        String[] credentials = decoded.split(":");
        createJWT(credentials);
    }

    private void createJWT(String[] credentials) {
        System.out.println("aca en createJWT");
        Optional<User> userToAuthenticate = userService.findByUsername(credentials[0]);
        System.out.println(userToAuthenticate);
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
            throw new IllegalArgumentException("Authentication not allowed");
        }
    }
}













