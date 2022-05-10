package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
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
import java.util.Optional;
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
        String name = "";
        if(user.get().getRole() == 1) {
            authorities.add(new SimpleGrantedAuthority("EMPLOYEE"));
            final Optional<Employee> employee = employeeService.getEmployeeById(user.get().getId());
            if(employee.isPresent())
                name = employee.get().getName();
        }
        else {
            authorities.add(new SimpleGrantedAuthority("EMPLOYER"));
            final Optional<Employer> employer = employerService.getEmployerById(user.get().getId());
            if(employer.isPresent())
                name = employer.get().getName();
        }
        return new HogarUser(username, password, authorities, name, user.get().getId());
    }
}