package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ExperienceService;
import ar.edu.itba.paw.service.MailingService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.form.EmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @RequestMapping("/crearPerfil")
    public ModelAndView createProfile(@ModelAttribute("employeeForm") final EmployeeForm form) {
        final ModelAndView mav = new ModelAndView("createProfile");
        return mav;
    }

    @RequestMapping(value = "/createEmployee", method = {RequestMethod.POST})
    public ModelAndView create(@Valid @ModelAttribute("employeeForm") final EmployeeForm form, final BindingResult errors){
        if(errors.hasErrors())
            return createProfile(form);
        final User u = userService.create(form.getMail());
        final Employee employee = employeeService.create(form.getName(), form.getLocation(), u.getId(), form.getAvailability(), form.getExperienceYears(), form.getAbilities());

        return new ModelAndView("redirect:/verPerfil");
    }
}
