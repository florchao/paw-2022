package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> user = us.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }
        String password = user.get().getPassword();
        System.out.print("password: ");
        System.out.println(password);
        System.out.println(BCRYPT_PATTERN.matcher(user.get().getPassword()).matches());
        if(Objects.equals(user.get().getPassword(), "pepe") || !BCRYPT_PATTERN.matcher(user.get().getPassword()).matches()){
            password = passwordEncoder.encode(user.get().getPassword());
            System.out.print("encripted password ");
            System.out.println(password);
            us.update(username, password);
        }
                final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                        new SimpleGrantedAuthority("ROLE_USER"),
                        new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new org.springframework.security.core.userdetails.User(username, password, authorities);
        }
    }