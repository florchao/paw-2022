package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AccessIsDeniedException;
import ar.edu.itba.paw.service.ApplicantService;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ApplicantService applicantService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewProfileController.class);
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/verPerfil", method = {RequestMethod.GET})
    public ModelAndView viewProfile() {
        final ModelAndView mav = new ModelAndView("viewProfile");
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findByUsername(principal.getUsername());
        if(user.isPresent()){
            mav.addObject("user", user.get());
            Optional<Employee> employee = employeeService.getEmployeeById(user.get().getId());
            employee.ifPresent(Employee::firstWordsToUpper);
            employee.ifPresent(value -> mav.addObject("employee", value));
            mav.addObject("userId", user.get().getId());
            Optional<List<Job>> jobs = applicantService.getJobsByApplicant(user.get().getId());
            System.out.println("TRABAJOS");
            for (Job job: jobs.get()) {
                System.out.print(job.getTitle());
                System.out.println("; ");
                System.out.println();
            }
        }
        return mav;
    }

    @RequestMapping(value = "/verPerfil/{userId}", method = RequestMethod.GET)
    public ModelAndView userProfile(@PathVariable("userId") final long userId, @RequestParam(value = "status", required = false) String status) {
        final ModelAndView mav = new ModelAndView("viewProfile");


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYEE")))
            throw new AccessIsDeniedException("Acces is denied");

        employeeService.isEmployee(userId);

        Optional<Employee> employee = employeeService.getEmployeeById(userId);

        if (employee.isPresent()) {
            employee.get().firstWordsToUpper();
            mav.addObject("employee", employee.get());
        }
        mav.addObject("status", status);

        if(auth.getAuthorities().contains(new SimpleGrantedAuthority("EMPLOYER"))) {
            HogarUser user = (HogarUser) auth.getPrincipal();
            Optional<Boolean> exists = contactService.existsContact(userId, user.getUserID());
            exists.ifPresent(aBoolean -> mav.addObject("contacted", aBoolean));
        }
        return mav;
    }


    @RequestMapping(value = "/user/profile-image/{userId}", method = {RequestMethod.GET})
    public void profileImage(HttpServletResponse response, @PathVariable final long userId) throws IOException {
        Optional<byte[]> image = userService.getProfileImage(userId);
        if(!image.isPresent()){
            LOGGER.debug("User {} does not have an image", userId);
            return;
        }
        InputStream is = new ByteArrayInputStream(image.get());
        IOUtils.copy(is,response.getOutputStream());
    }
}
