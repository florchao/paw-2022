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
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canEditEmployee(authentication, #employeeId)")
                .antMatchers(HttpMethod.POST, "/api/applicants").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.PUT, "/api/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canDeleteJob(authentication, #jobId)")
                .antMatchers(HttpMethod.DELETE, "/api/applicants/{employeeId}/{jobId}").access("@antMatcherVoter.canEditEmployee(authentication, #employeeId)")
                .antMatchers(HttpMethod.POST, "/api/contacts/us").permitAll()
                .antMatchers(HttpMethod.POST, "/api/contacts").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.GET, "/api/contacts/{employeeId}/{employerId}").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/api/employees").access("hasAuthority('EMPLOYER') or isAnonymous()")
                .antMatchers(HttpMethod.GET, "/api/employee/{id}").access("@antMatcherVoter.canAccessEmployeeProfile(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/employee/{id}/jobs").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/employee/{id}/contacts").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/employee/{id}/reviews").authenticated()
                .antMatchers(HttpMethod.GET, "/api/employee/{employeeId}/reviews/{employerId}").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/api/employee").anonymous()
                .antMatchers(HttpMethod.PUT, "/api/employee/{id}").access("@antMatcherVoter.canEditEmployee(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/employers/{id}").access("@antMatcherVoter.canAccessEmployerProfile(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/employers/{id}/jobs").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.GET, "/api/employers/{id}/reviews").authenticated()
                .antMatchers(HttpMethod.GET, "/api/employers/{employerId}/reviews/{employeeId}").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/api/employers").anonymous()
                .antMatchers(HttpMethod.GET, "/api/assets/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/jobs").hasAuthority("EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/api/jobs/{id}").authenticated()
                .antMatchers(HttpMethod.GET, "/api/applicants/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.POST, "/api/jobs").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.DELETE, "/api/jobs/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.PUT, "/api/jobs/{id}").access("@antMatcherVoter.canDeleteJob(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/ratings/{employeeId}/{employerId}").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/api/ratings").hasAuthority("EMPLOYER")
                .antMatchers(HttpMethod.POST, "/api/reviews").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/users").anonymous()
                .antMatchers(HttpMethod.GET, "/api/user").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/users/{id}").access("@antMatcherVoter.canDeleteUser(authentication, #id)")
                .antMatchers(HttpMethod.GET, "/api/ids").permitAll()
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
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
