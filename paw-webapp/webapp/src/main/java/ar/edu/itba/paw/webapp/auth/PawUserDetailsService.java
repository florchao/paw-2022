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

import java.util.*;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("No user by the name " + username);
        }
        String password = user.get().getPassword();
        if(Objects.equals(user.get().getPassword(), "pepe") || !BCRYPT_PATTERN.matcher(user.get().getPassword()).matches()){
            userService.update(username, user.get().getPassword());
        }
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println(user.get().getRole());
        if(user.get().getRole() == 1)
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
        else
            authorities.add(new SimpleGrantedAuthority("EMPLOYER"));
        org.springframework.security.core.userdetails.User uuuu = new org.springframework.security.core.userdetails.User(username, password, authorities);
        System.out.println(uuuu.getAuthorities());
        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }
}