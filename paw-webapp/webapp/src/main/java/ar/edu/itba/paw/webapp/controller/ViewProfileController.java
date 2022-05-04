package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
public class ViewProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping("/verPerfil")
    public ModelAndView viewProfile() {
        final ModelAndView mav = new ModelAndView("viewProfile");
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findByUsername(principal.getUsername());
        if(user.isPresent()){
            mav.addObject("user", user.get());
            Optional<Employee> employee = employeeService.getEmployeeById(user.get().getId());
            employee.ifPresent(value -> mav.addObject("employee", value));
        }
        return mav;
    }

    @RequestMapping("/verPerfil/{userId}")
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("viewProfile");
        ModelAndView errorPage = new ModelAndView("errorPage");

        Optional<User> user;
        try{
            user = userService.getUserById(userId);
        }
        catch (UserNotFoundException uex){
            errorPage.addObject("errorMsg", uex.getMessage());
            return errorPage;
        }
        user.ifPresent(value -> mav.addObject("user", value));

        Optional<Employee> employee = employeeService.getEmployeeById(userId);

        employee.ifPresent(value -> mav.addObject("employee", firstWordsToUpper(value)));

        return mav;
    }

    Employee firstWordsToUpper(Employee employee) {
        StringBuilder finalName = new StringBuilder();
        for (String word : employee.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        employee.setName(finalName.toString());
        return employee;

    }
}
