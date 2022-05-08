package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping("/contactos")
    public ModelAndView contactsPage() {
        final ModelAndView mav = new ModelAndView("contacts");
        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<List<Contact>> list = contactService.getAllContacts(principal.getUserID());
        list.ifPresent(contacts -> mav.addObject("ContactList", contacts));
        return mav;
    }

    @RequestMapping("/contacto/{id}")
    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(UserNotFoundException::new);
        mav.addObject("name", employee.getName());
        return mav;
    }

    @RequestMapping(value = "/contactarEmpleado/{id}", method = {RequestMethod.POST})
    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @PathVariable int id) {
        ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+id);
        if(errors.hasErrors())
            return contactPage(form, id);
        Optional<User> user = userService.getUserById(id);
        HogarUser principal = (HogarUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try{
            user.ifPresent(value -> contactService.contact(value, form.getContent(), principal.getName(), form.getPhone()));
            mav.addObject("status", "sent");
        }
        catch (AlreadyExistsException alreadyExistsException){
            mav.addObject("status", "error");
        }
        return mav;
    }

    @RequestMapping("/contactanos")
    public ModelAndView contactPage(@ModelAttribute("contactUsForm") final ContactUsForm form, @RequestParam(value = "status", required = false) String status) {
        final ModelAndView mav = new ModelAndView("contactUs");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof HogarUser){
            HogarUser hogarUser = (HogarUser) principal;
            mav.addObject("name", hogarUser.getName());
            mav.addObject("mail", hogarUser.getUsername());
        }
        mav.addObject("status", status);
        return mav;
    }

    @RequestMapping(value = "/contactUs", method = {RequestMethod.POST})
    public ModelAndView contactUs(@Valid @ModelAttribute("contactUsForm") final ContactUsForm form, BindingResult error) {
        if(error.hasErrors())
            return contactPage(form, "error");
        final ModelAndView mav = new ModelAndView("redirect:/contactanos");
        contactService.contactUS(form.getContent(), form.getMail(), form.getName());
        mav.addObject("status", "sent");
        return mav;
    }
}
