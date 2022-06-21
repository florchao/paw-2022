package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Abilities;
import ar.edu.itba.paw.model.Availability;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.ImagesService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.EmployeeEditForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private ImagesService imagesService;
    private static final Logger LOGGER = LoggerFactory.getLogger(EditProfileController.class);

    @RequestMapping(value = "/editarPerfil", method = {RequestMethod.GET})
    public ModelAndView editProfile(@ModelAttribute("employeeEditForm") final EmployeeEditForm form) throws UserNotFoundException {
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Employee> employee = employeeService.getEmployeeById(principal.getUserID());
        final ModelAndView mav = new ModelAndView("editProfile");
        mav.addObject("abilities", Abilities.getIds());
        mav.addObject("availability", Availability.getIds());
        if(employee.isPresent()) {
            System.out.print("array: ");
            System.out.println(employee.get().getAbilitiesArr());
            form.setAbilities(new String[]{String.join(", ", employee.get().getAbilitiesArr())});
            form.setAbilities(employee.get().getAbilitiesArr().toArray(new String[0]));
            form.setAvailability(employee.get().getAvailabilityArr().toArray(new String[0]));
            form.setLocation(employee.get().getLocation());
            form.setName(employee.get().getName());
            form.setExperienceYears(employee.get().getExperienceYears());
        }
        mav.addObject("userId", principal.getUserID());
        return mav;
    }

    @RequestMapping(value = "/editEmployee", method = {RequestMethod.POST})
    public ModelAndView edit(@Valid @ModelAttribute("employeeEditForm") final EmployeeEditForm form, final BindingResult errors) throws UserNotFoundException {

        if(errors.hasErrors()) {
            LOGGER.debug("Could not update profile");
            return editProfile(form);
        }
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        employeeService.editProfile(form.getName().toLowerCase(), form.getLocation().toLowerCase(), (principal.getUserID()), form.getAvailability(), form.getExperienceYears(), form.getAbilities(), form.getImage().getBytes());
        LOGGER.debug(String.format("updated profile for userid %d", principal.getUserID()));
        return new ModelAndView("redirect:/verPerfil/");
    }
}
