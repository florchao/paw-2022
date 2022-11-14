package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and().sessionManagement()
                .invalidSessionUrl("/login")
                .and().authorizeRequests()
                .antMatchers("/", "/api/explore/employees", "/api/explore/jobs", "/api/profile/hello", "/api/profile/{userId}", "/api/job/{userId}", "/api/profile/image/{userId}", "/api/review/employee/{userId}", "/api/rating/{userId}", "/filterEmployees", "/api/contact/us", "/api/contact/{id}","/user/profile-image/{userId}", "/contactUs").permitAll()
                .antMatchers("/login", "/registrarse", "/register", "/nuevaContrasena", "/newPassword", "/crearPerfilEmpleador", "/createEmployer", "/crearPerfil", "/createEmployee").anonymous()
                .antMatchers("/verPerfil/{userId}", "/buscarEmpleadas").not().hasAuthority("EMPLOYEE")
                .antMatchers("/contactos", "/editarPerfil", "/editEmployee", "/trabajos", "/filterJobs", "/apply/{jobID}", "/trabajosAplicados").hasAuthority("EMPLOYEE")
                .antMatchers("/contacto/{id}", "/contactEmployee/{id}", "/misTrabajos", "/crearTrabajo", "/createJob", "/aplicantes/{jobID}", "/addRating/{idRating}", "/changeStatus/{jobId}/{employeeId}/{status}", "/deleteJob/{jobId}", "/closeJob/{jobId}", "/openJob/{jobId}", "addReview/{id}", "/inicio").hasAuthority("EMPLOYER")
                .antMatchers("/**").authenticated()
                .and().formLogin()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .defaultSuccessUrl("/afterLogin", false)
                .loginPage("/login").failureUrl("/login?error=true") //en el model and view chequeas si el param es false
                .and().rememberMe()
                .rememberMeParameter("j_rememberme")
                .userDetailsService(userDetailsService)
                .key("19f1ba81f376bd6e6a84fbaa06022a1c6dffcc4702116248cc18e7b317002ed8114ee13f36839d496d144ac43c17a2f88b65b9096f7d1956f43a3e9c97eddb258b63a7af73903a54acf397311e3eae0ee2e6cbee213f8f4b29156969ea6848e1014261c3391ff679d25a263aa5a4f63d62c3586ed34106cdf04011ed7b014131dac0e22ac1c07171983dc1d02bcd23a7f04cda2ef21764417d4b7754a4629f02") // no hacer esto, crear una aleatoria segura suficiente mente grande y colocarla bajo src/main/resources
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
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
