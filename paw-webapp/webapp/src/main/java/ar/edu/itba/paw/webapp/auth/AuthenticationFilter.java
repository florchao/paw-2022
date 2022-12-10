package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    @Autowired
    private UserService userService;

    @Autowired
    private PawUserDetailsService pawUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final List<Pair> blackList = new ArrayList<>();

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        Pair requestPair = new Pair(request.getMethod(), request.getRequestURI());
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/applicants/{employeeId}/{jobId}"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/applicants/{employeeId}/{jobId}"));
//        blackList.add(new Pair(HttpMethod.POST, "/api/applicants"));
//        blackList.add(new Pair(HttpMethod.PUT, "/api/applicants/{employeeId}/{jobId}"));
//        blackList.add(new Pair(HttpMethod.DELETE, "/api/applicants/{employeeId}/{jobId}"));
//        blackList.add(new Pair(HttpMethod.POST, "/api/contacts/us"));
//        blackList.add(new Pair(HttpMethod.POST, "/api/contacts"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/contacts/{employeeId}/{employerId}"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees/{id}/jobs"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees/{id}/contacts"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees/{id}/reviews"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employees/{employeeId}/reviews/{employerId}"));
//        blackList.add(new Pair(HttpMethod.POST, "/api/employees"));
//        blackList.add(new Pair(HttpMethod.PUT, "/api/employees/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employers/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employers/{id}/jobs"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employers/{id}/reviews"));
//        blackList.add(new Pair(HttpMethod.GET, "/api/employers/{employerId}/reviews/{employeeId}"));
//        blackList.add(new Pair(HttpMethod.POST, "/api/employers"));
//        blackList.add(new Pair(HttpMethod.GET, "api/images/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "api/jobs"));
//        blackList.add(new Pair(HttpMethod.GET, "api/jobs/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "api/jobs/{id}/applicants"));
//        blackList.add(new Pair(HttpMethod.POST, "api/jobs"));
//        blackList.add(new Pair(HttpMethod.DELETE, "api/jobs/{id}"));
//        blackList.add(new Pair(HttpMethod.GET, "api/ratings/{employeeId}/{employerId}"));
//        blackList.add(new Pair(HttpMethod.POST, "api/ratings"));
//        blackList.add(new Pair(HttpMethod.POST, "api/reviews"));
//        blackList.add(new Pair(HttpMethod.PUT, "api/users"));
//        blackList.add(new Pair(HttpMethod.DELETE, "api/users/{id}"));
//        return blackList.contains(requestPair);
//    }

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
            throw new InsufficientAuthenticationException("Invalid credentials");
        }
        UserDetails userDetails = pawUserDetailsService.loadUserByUsername(credentials[0]);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) RequestContextHolder.getRequestAttributes()));
        return createJWT(credentials);
    }

    private String createJWT(String[] credentials) throws UserNotFoundException, IllegalArgumentException {
        Optional<User> algo = userService.findByUsername(credentials[0]);
        User user = algo.orElseThrow(() -> new UserNotFoundException("User not found"));

        //TODO save secret key as env variable
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = null;
        try {
            jwtToken = Jwts.builder()
                    .claim("email", credentials[0])
                    .claim("role", user.getId() == 1 ? "EMPLOYER" : "EMPLOYEE")
                    .claim("uid", user.getId())
                    .setIssuedAt(Date.from(Instant.now()))
                    .setExpiration(Date.from(Instant.now().plus(10000L, ChronoUnit.HOURS)))
                    .signWith(hmacKey)
                    .compact();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return jwtToken;
    }

    private void bearerAuthentication(String bearerAuth) throws InsufficientAuthenticationException {
        int BEARER_TOKEN_LENGTH = 7;

        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        String jwt = bearerAuth.substring(BEARER_TOKEN_LENGTH);
        try {
            Jws<Claims> parsed = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(jwt);
    //        System.out.println(parsed.getBody());
            UserDetails userDetails = pawUserDetailsService.loadUserByUsername(parsed.getBody().get("email").toString());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails == null ? Collections.emptyList() : userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            throw new InsufficientAuthenticationException("Invalid token");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderContent = httpServletRequest.getHeader("Authorization");
        if (authHeaderContent != null && authHeaderContent.startsWith("Basic")) {
            try {
                String jwt = basicAuthentication(authHeaderContent);
                httpServletResponse.addHeader("Authorization", "Bearer " + jwt);
            } catch (Exception e) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
            }
        } else if (authHeaderContent != null && authHeaderContent.startsWith("Bearer")) {
            try {
                bearerAuthentication(authHeaderContent);
            } catch (Exception e) {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            }
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
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












