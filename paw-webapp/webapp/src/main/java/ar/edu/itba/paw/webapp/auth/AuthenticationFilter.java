package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private Environment environment;

    @Autowired
    private UserService userService;

    @Autowired
    private PawUserDetailsService pawUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;


    private String basicAuthentication(String basicAuth) throws UserNotFoundException, IllegalArgumentException, InsufficientAuthenticationException {
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
            throw new InsufficientAuthenticationException("Invalid credentials", e);
        }
        UserDetails userDetails = pawUserDetailsService.loadUserByUsername(credentials[0]);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return createJWT(credentials);
    }

    private String createJWT(String[] credentials) throws UserNotFoundException, IllegalArgumentException {
        User user = userService.findByUsername(credentials[0]);
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(environment.getRequiredProperty("db.auth.secret")),
                SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = null;
        try {
            jwtToken = Jwts.builder()
                    .claim("email", credentials[0])
                    .claim("role", user.getRole() == 1 ? "EMPLOYEE" : "EMPLOYER")
                    .claim("uid", user.getId())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(365L, ChronoUnit.DAYS)))
                    .signWith(hmacKey)
                    .compact();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password", e);
        }
        return jwtToken;
    }

    private void bearerAuthentication(String bearerAuth) throws InsufficientAuthenticationException {
        int BEARER_TOKEN_LENGTH = 7;

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(environment.getRequiredProperty("db.auth.secret")),
                SignatureAlgorithm.HS256.getJcaName());

        String jwt = bearerAuth.substring(BEARER_TOKEN_LENGTH);
        try {
            Jws<Claims> parsed = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(jwt);
            UserDetails userDetails = pawUserDetailsService.loadUserByUsername(parsed.getBody().get("email").toString());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (ExpiredJwtException exception) {
            throw new InsufficientAuthenticationException("Expired token", exception);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InsufficientAuthenticationException("Invalid token", e);
        } catch (InvalidClaimException e) {
            throw new InsufficientAuthenticationException("Invalid value for claim \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InsufficientAuthenticationException("Invalid token", e);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderContent = httpServletRequest.getHeader("Authorization");
        if (authHeaderContent == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (authHeaderContent.startsWith("Basic")) {
            try {
                String jwt = basicAuthentication(authHeaderContent);
                httpServletResponse.addHeader("Authorization", "Bearer " + jwt);
            } catch (Exception e) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
                return;
            }
        } else if (authHeaderContent.startsWith("Bearer")) {
            try {
                bearerAuthentication(authHeaderContent);
            } catch (Exception e) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    class Pair {
        private final String method;
        private final String path;

        Pair(String method, String path) {
            this.method = method;
            this.path = path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair pair = (Pair) o;

            if (!Objects.equals(method, pair.method)) return false;
            return Objects.equals(path, pair.path);
        }

        @Override
        public int hashCode() {
            int result = method != null ? method.hashCode() : 0;
            result = 31 * result + (path != null ? path.hashCode() : 0);
            return result;
        }
    }
}












