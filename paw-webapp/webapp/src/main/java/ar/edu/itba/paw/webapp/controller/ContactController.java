package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ContactController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ContactService contactService;

//    @RequestMapping(value = "/contactRedirect", method = RequestMethod.GET)
//    public ModelAndView contactRedirect(@RequestParam("userId") Long id) {
//        final ModelAndView mav = new ModelAndView("redirect:/contacto");
//        mav.addObject("user",userService.getUserById(id));
//        return mav;
//    }

    @RequestMapping("/contacto/{id}")
    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        employee.ifPresent(value -> {mav.addObject("name", value.getName()); System.out.println(employee.get().getName());});
        return mav;
    }

    @RequestMapping(value = "/contactarEmpleado/{id}", method = {RequestMethod.POST})
    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @PathVariable int id) {
        if(errors.hasErrors())
            return contactPage(form, id);
        Optional<User> user = userService.getUserById(id);
        user.ifPresent(value -> contactService.contact(form.getEmail(), value.getUsername(), form.getName(), form.getContent()));
        return new ModelAndView("redirect:/verPerfil/"+id);
    }
}
