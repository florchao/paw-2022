package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.UserProfileForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            employee.get().firstWordsToUpper();
            employee.ifPresent(value -> mav.addObject("employee", value));
            mav.addObject("userId", user.get().getId());
        }
        return mav;
    }

    @RequestMapping(value = "/verPerfil/{userId}", method = RequestMethod.GET)
    public ModelAndView userProfile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("viewProfile");
        ModelAndView errorPage = new ModelAndView("errorPage");

        User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        mav.addObject("user", user);

        Optional<Employee> employee = employeeService.getEmployeeById(userId);

        if (employee.isPresent()) {
            employee.get().firstWordsToUpper();
            mav.addObject("employee", employee.get());
        }

        return mav;
    }


    @RequestMapping("/user/profile-image/{userId}")
    public void profileImage(HttpServletResponse response, @PathVariable final long userId) throws IOException {
        Optional<byte[]> image = userService.getProfileImage(userId);
        if(image.isPresent()){
            InputStream is = new ByteArrayInputStream(image.get());
            IOUtils.copy(is,response.getOutputStream());
        }

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
