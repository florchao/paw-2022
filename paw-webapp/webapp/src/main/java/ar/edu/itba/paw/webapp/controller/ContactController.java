package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.ContactExistsException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
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
import java.util.List;
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

    @Autowired
    private EmployerService employerService;

    @RequestMapping("/contacto/{id}")
    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        employee.ifPresent(value -> {mav.addObject("name", value.getName());});
        return mav;
    }

    @RequestMapping("/contactos")
    public ModelAndView contactsPage() {
        List<Contact> list = contactService.getAllContacts().get();
        final ModelAndView mav = new ModelAndView("contacts");
        mav.addObject("ContactList", list);
        return mav;
    }

    @RequestMapping(value = "/contactarEmpleado/{id}", method = {RequestMethod.POST})
    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @PathVariable int id) {
        ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+id);
        if(errors.hasErrors())
            return contactPage(form, id);
        Optional<User> user = userService.getUserById(id);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails)
            username = ((UserDetails)principal).getUsername();
        Optional<User> current = userService.findByUsername(username);
        Optional<Employer> employer = employerService.getEmployerById(current.get().getId());
        try{
            user.ifPresent(value -> contactService.contact(value, form.getContent(), employer.get().getName(), form.getPhone()));
        }
        catch (ContactExistsException contactException){
            mav.addObject("ContactError", contactException.getMessage());
        }
        return mav;
    }

    @RequestMapping("/contactanos")
    public ModelAndView contactPage(@ModelAttribute("contactUsForm") final ContactUsForm form) {
        final ModelAndView mav = new ModelAndView("contactUs");
        mav.addObject("name", "AAAAA");

        return mav;
    }

    @RequestMapping(value = "/contactUs", method = {RequestMethod.POST})
    public ModelAndView contactUs(@Valid @ModelAttribute("contactUsForm") final ContactUsForm form, BindingResult error) {
        return new ModelAndView("redirect:/contactUs");
    }
}
