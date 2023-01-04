package ar.edu.itba.paw.webapp.voters;

import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
public class AntMatcherVoter {

    @Autowired
    private EmployeeService employeeService;

    public boolean canAccessEmployeeProfile(Authentication auth, Long id) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            return user.getUserID() == id;
        }
        return true;
    }

    public boolean canEditEmployee(Authentication auth, Long id) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
            HogarUser user = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user.getUserID() == id;
        } else{
            return false;
        }
    }

    public boolean canAccessEmployerProfile(Authentication auth, Long id) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            return user.getUserID() == id;
        }
        return false;
    }

    public boolean canDeleteUser(Authentication auth, Long id){
        if(auth.isAuthenticated()){
            HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return hogarUser.getUserID() == id;
        }
        return false;
    }

}
