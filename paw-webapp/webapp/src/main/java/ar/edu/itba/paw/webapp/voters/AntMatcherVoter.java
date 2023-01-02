package ar.edu.itba.paw.webapp.voters;

import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AntMatcherVoter {

    @Autowired
    private EmployeeService employeeService;

    public boolean canAccessEmployeeProfile(Authentication auth, Long id) {
        if (auth instanceof AnonymousAuthenticationToken) return false;
        HogarUser user = null;
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            user = (HogarUser) auth.getPrincipal();
            if (user.getUserID() != id) {
                return false;
            }
        }
        return false;
    }

    public boolean canEditEmployee(Authentication auth, Long id) {
        HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserID() == id;
    }

}
