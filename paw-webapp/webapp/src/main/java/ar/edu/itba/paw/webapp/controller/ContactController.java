package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.ContactExistsException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

//    @RequestMapping(value = "/contactRedirect", method = RequestMethod.GET)
//    public ModelAndView contactRedirect(@RequestParam("userId") Long id) {
//        final ModelAndView mav = new ModelAndView("redirect:/contacto");
//        mav.addObject("user",userService.getUserById(id));
//        return mav;
//    }

    @RequestMapping("/contacto/{id}")
    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) {
        final ModelAndView mav = new ModelAndView("contactForm");
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(UserNotFoundException::new);
        mav.addObject("name", employee.getName());
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
        try{
            user.ifPresent(value -> contactService.contact(value, form.getContent(), form.getName(), form.getPhone()));
        }
        catch (ContactExistsException contactException){
            mav.addObject("ContactError", contactException.getMessage());
        }
        return mav;
    }
}
