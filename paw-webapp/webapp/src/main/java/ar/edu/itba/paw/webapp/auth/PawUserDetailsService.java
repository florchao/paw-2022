package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.EmployerService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployerService employerService;
    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException, UserNotFoundException {
        final User user = userService.findByUsername(username);
        String password = user.getPassword();
        if (Objects.equals(user.getPassword(), "pepe") || !BCRYPT_PATTERN.matcher(user.getPassword()).matches()) {
            userService.update(username, user.getPassword());
        }
        final Collection<GrantedAuthority> authorities = new ArrayList<>();
        String name = "";
        if (user.getRole() == 1) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            final Employee employee;
            employee = employeeService.getEmployeeById(user.getId());
            name = employee.getName();
        } else {
            authorities.add(new SimpleGrantedAuthority("EMPLOYER"));
            final Employer employer = employerService.getEmployerById(user.getId());
            name = employer.getName();
        }
        return new HogarUser(username, password, authorities, name, user.getId());
    }
}