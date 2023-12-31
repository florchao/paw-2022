package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.AuthenticationFilter;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.voters.AntMatcherVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth", "ar.edu.itba.paw.webapp.voters"})
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public AntMatcherVoter antMatcherVoter() { return new AntMatcherVoter();}

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and().headers().cacheControl().disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canEditEmployee(authentication, #employeeId)")
                .antMatchers(HttpMethod.POST, "/applicants").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.PUT, "/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canDeleteJob(authentication, #jobId)")
                .antMatchers(HttpMethod.DELETE, "/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canEditEmployee(authentication, #employeeId)")
                .antMatchers(HttpMethod.POST, "/contacts/us").permitAll()
                .antMatchers(HttpMethod.POST, "/contacts").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.GET, "/contacts/{employeeId}/{employerId}").access("hasAuthority('EMPLOYER') or hasAuthority('EMPLOYEE')")
                .antMatchers(HttpMethod.GET, "/employees").access("hasAuthority('EMPLOYER') or isAnonymous()")
                .antMatchers(HttpMethod.GET, "/employees/{id}").access("@antMatcherVoter.canAccessEmployeeProfile(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employees/{id}/jobs").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employees/{id}/contacts").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employees/{id}/reviews").authenticated()
                .antMatchers(HttpMethod.GET, "/employees/{employeeId}/reviews/{employerId}").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/employees").anonymous()
                .antMatchers(HttpMethod.PUT, "/employees/{id}").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employers/{id}").access("@antMatcherVoter.canAccessEmployerProfile(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employers/{id}/edit").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/employers/{id}/jobs").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.GET, "/employers/{id}/reviews").authenticated()
                .antMatchers(HttpMethod.GET, "/employers/{employerId}/reviews/{employeeId}").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/employers").anonymous()
                .antMatchers(HttpMethod.GET, "/assets/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/jobs").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/jobs/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/applicants/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.POST, "/jobs").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.DELETE, "/jobs/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.PUT, "/jobs/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/ratings/{employeeId}/{employerId}").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/ratings").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/reviews").authenticated()
                .antMatchers(HttpMethod.GET, "/reviews/{employeeId}/{employerId}/{forEmployee}").access("@antMatcherVoter.canViewReview(authentication, #employeeId, #employerId)")
                .antMatchers(HttpMethod.PUT, "/users").anonymous()
                .antMatchers(HttpMethod.GET, "/user").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/{id}").access("@antMatcherVoter.canDeleteUser(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/availabilities").permitAll()
                .antMatchers(HttpMethod.GET, "/abilities").permitAll()
                .antMatchers(HttpMethod.GET, "/locations").permitAll()
                .antMatchers(HttpMethod.GET, "/images/{id}").permitAll()
                .antMatchers(HttpMethod.POST, "/images").authenticated()
                .antMatchers(HttpMethod.PUT, "/images/{id}").access("@antMatcherVoter.canUploadImage(authentication, #id)")
                .and().antMatcher("/**").authorizeRequests()
                .and().addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/img/**", " /favicon.ico", "/403", "/public/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Link", "Location", "ETag", "Total-Count"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
