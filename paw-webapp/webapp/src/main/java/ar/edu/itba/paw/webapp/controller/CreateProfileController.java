package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
import ar.edu.itba.paw.webapp.form.EmployerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CreateProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployerService employerService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping("/crearPerfil/{userID}")
    public ModelAndView createProfile(@ModelAttribute("employeeForm") final EmployeeForm form, @PathVariable String userID) {
        final ModelAndView mav = new ModelAndView("createProfile");
        return mav;
    }

    @RequestMapping(value = "/createEmployee/{userID}", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("employeeForm") final EmployeeForm form, final BindingResult errors, @PathVariable String userID){
        if(errors.hasErrors())
            return createProfile(form, userID);

        final Employee employee = employeeService.create(form.getName().toLowerCase(), form.getLocation().toLowerCase(), Long.parseLong(userID), form.getAvailability(), form.getExperienceYears(), form.getAbilities(), form.getImage().getBytes());
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.setName(form.getName());
        return new ModelAndView("redirect:/verPerfil/"+employee.getId());
    }

    @RequestMapping("/crearPerfilEmpleador/{userID}")
    public ModelAndView createProfileEmployer(@ModelAttribute("employerForm") final EmployerForm form, @PathVariable String userID) {
        final ModelAndView mav = new ModelAndView("createProfileEmployer");
        return mav;
    }

    @RequestMapping(value = "/createEmployer/{userID}", method = {RequestMethod.POST})
    public ModelAndView createEmployer(@Valid @ModelAttribute("employerForm") final EmployerForm form, final BindingResult errors, @PathVariable String userID){
        if(errors.hasErrors())
            return createProfileEmployer(form, userID);
        String name = form.getName() + " " + form.getLastname();
        System.out.println(userID);
        final Employer employer = employerService.create(name.toLowerCase(), Long.parseLong(userID), form.getImage().getBytes());
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.setName(name);
        return new ModelAndView("redirect:/buscarEmpleadas");
    }

}
