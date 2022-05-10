package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.EmployeeEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EditProfileController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;

    @RequestMapping("/editarPerfil")
    public ModelAndView editProfile(@ModelAttribute("employeeEditForm") final EmployeeEditForm form) {
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Employee> employee = employeeService.getEmployeeById(principal.getUserID());
        final ModelAndView mav = new ModelAndView("editProfile");
        form.setAbilities(new String[]{String.join(", ", employee.get().getAbilitiesArr())});
        form.setAbilities(employee.get().getAbilitiesArr().toArray(new String[employee.get().getAbilitiesArr().size()]));
        form.setAvailability(employee.get().getAvailabilityArr().toArray(new String[employee.get().getAvailabilityArr().size()]));
        form.setLocation(employee.get().getLocation());
        form.setName(employee.get().getName());
        form.setExperienceYears(employee.get().getExperienceYears());
        mav.addObject("userId", principal.getUserID());
        return mav;
    }

    @RequestMapping(value = "/editEmployee", method = {RequestMethod.POST})
    public ModelAndView edit(@Valid @ModelAttribute("employeeEditForm") final EmployeeEditForm form, final BindingResult errors){

        if(errors.hasErrors())
            return editProfile(form);
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        employeeService.editProfile(form.getName().toLowerCase(), form.getLocation().toLowerCase(), ((Long) principal.getUserID()), form.getAvailability(), form.getExperienceYears(), form.getAbilities());
        if(!form.getImage().isEmpty())
            userService.updateProfileImage(((Long) principal.getUserID()), form.getImage().getBytes());
        return new ModelAndView("redirect:/verPerfil/");
    }
}
