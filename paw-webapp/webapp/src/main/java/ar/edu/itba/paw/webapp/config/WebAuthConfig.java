package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.AuthenticationFilter;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
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


import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and()
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .and().antMatcher("/**").authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/applicants/{employeeId}/{jobId}").anonymous()
                .antMatchers(HttpMethod.POST, "/api/applicants").anonymous()
                .antMatchers(HttpMethod.PUT, "/api/applicants/{employeeId}/{jobId}").anonymous()
                .antMatchers(HttpMethod.DELETE, "/api/applicants/{employeeId}/{jobId}").anonymous()
                .antMatchers(HttpMethod.POST, "/api/contacts/us").anonymous()
                .antMatchers(HttpMethod.POST, "/api/contacts").anonymous()
                .antMatchers(HttpMethod.GET, "/api/contacts/{employeeId}/{employerId}").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees/{id}").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees/{id}/jobs").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees/{id}/contacts").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees/{id}/reviews").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employees/{employeeId}/reviews/{employerId}").anonymous()
                .antMatchers(HttpMethod.POST,"/api/employees").anonymous()
                .antMatchers(HttpMethod.PUT,"/api/employees/{id}").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employers/{id}").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employers/{id}/jobs").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employers/{id}/reviews").anonymous()
                .antMatchers(HttpMethod.GET,"/api/employers/{employerId}/reviews/{employeeId}").anonymous()
                .antMatchers(HttpMethod.POST,"/api/employers").anonymous()
                .antMatchers(HttpMethod.GET, "api/images/{id}").anonymous()
                .antMatchers(HttpMethod.GET, "api/jobs").anonymous()
                .antMatchers(HttpMethod.GET, "api/jobs/{id}").anonymous()
                .antMatchers(HttpMethod.GET, "api/jobs/{id}/applicants").anonymous()
                .antMatchers(HttpMethod.POST, "api/jobs").anonymous()
                .antMatchers(HttpMethod.DELETE, "api/jobs/{id}").anonymous()
                .antMatchers(HttpMethod.GET, "api/ratings/{employeeId}/{employerId}").anonymous()
                .antMatchers(HttpMethod.POST, "api/ratings").anonymous()
                .antMatchers(HttpMethod.POST, "api/reviews").anonymous()
                .antMatchers(HttpMethod.PUT, "api/users").anonymous()
                .antMatchers(HttpMethod.DELETE, "api/users/{id}").anonymous()
                .antMatchers("/**").anonymous()

                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
//                .and().csrf().disable();
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
