package ar.edu.itba.paw.webapp.voters;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.service.JobService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class AntMatcherVoter {

    @Autowired
    private JobService jobService;

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
        } else {
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

    public boolean canDeleteUser(Authentication auth, Long id) {
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return hogarUser.getUserID() == id;
        }
        return false;
    }

    public boolean canUploadImage(Authentication auth, Long id) {
        if (!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return hogarUser.getUserID() == id;
        }
        return false;
    }

    public boolean canDeleteJob(Authentication authentication, Long id) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            Job job;
            try {
                job = jobService.getJobByID(id);
            } catch (Exception exception) {
                return true;
            }
            HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return hogarUser.getUserID() == job.getEmployerId().getId().getId();
        }
        return false;
    }

    public boolean canViewReview(Authentication authentication, Long employeeId, Long employerId){
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            return false;
        HogarUser hogarUser = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return employerId == hogarUser.getUserID() || employeeId == hogarUser.getUserID();
    }
}
