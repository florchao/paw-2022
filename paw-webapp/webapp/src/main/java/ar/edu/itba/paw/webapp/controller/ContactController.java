package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;
import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.EmployeeService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.auth.HogarUser;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Path("/api/contact")
@Component
public class ContactController {

    private final int PAGE_SIZE = 8;
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ContactService contactService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);

//    @RequestMapping(value = "/contactos", method = {RequestMethod.GET})
//    public ModelAndView contactsPage(@RequestParam(value = "page", required = false) Long page) throws UserNotFoundException {
//        final ModelAndView mav = new ModelAndView("contacts");
//        if (page == null)
//            page = 0L;
//        HogarUser principal = (HogarUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<Contact> list = contactService.getAllContacts(principal.getUserID(), page, PAGE_SIZE);
//        for (Contact contact : list) {
//            contact.firstWordsToUpper();
//
//        }
//        mav.addObject("ContactList", list);
//        mav.addObject("page", page);
//        int maxPage = contactService.getPageNumber(principal.getUserID(), PAGE_SIZE);
//        mav.addObject("maxPage", maxPage);
//        return mav;
//    }

//    @RequestMapping(value = "/contacto/{id}", method = {RequestMethod.GET})
//    public ModelAndView contactPage(@ModelAttribute("contactForm") final ContactForm form, @PathVariable final int id) throws UserNotFoundException {
//        final ModelAndView mav = new ModelAndView("contactForm");
//        try {
//            userService.getUserById(id);
//        } catch (ar.edu.itba.paw.model.exception.UserNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        employeeService.isEmployee(id);
//        Optional<Employee> employee = employeeService.getEmployeeById(id);
//        if(employee.isPresent()){
//            employee.get().firstWordsToUpper();
//            mav.addObject("name", employee.get().getName());
//        }
//        return mav;
//    }

//    @RequestMapping(value = "/contactEmployee/{id}", method = {RequestMethod.POST})
//    public ModelAndView contactEmployee(@Valid @ModelAttribute("contactForm") final ContactForm form, final BindingResult errors, @PathVariable int id) throws UserNotFoundException, AlreadyExistsException {
//        ModelAndView mav = new ModelAndView("redirect:/verPerfil/"+id);
//        if(errors.hasErrors()) {
//            LOGGER.debug("Couldn't contact employee");
//            return contactPage(form, id);
//        }
//        User user = userService.getUserById(id);
//        HogarUser principal = (HogarUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(contactService.contact(user, form.getContent(), principal.getName(), form.getPhone()))
//            mav.addObject("status", "sent");
//        else
//            mav.addObject("status", "error");
//        return mav;
//    }

//    @RequestMapping(value = "/contactanos", method = {RequestMethod.GET})
//    public ModelAndView contactPage(@ModelAttribute("contactUsForm") final ContactUsForm form, @RequestParam(value = "status", required = false) String status) {
//        final ModelAndView mav = new ModelAndView("contactUs");
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(principal instanceof HogarUser){
//            HogarUser hogarUser = (HogarUser) principal;
//            hogarUser.firstWordsToUpper();
//            mav.addObject("name", hogarUser.getName());
//            mav.addObject("mail", hogarUser.getUsername());
//        }
//        mav.addObject("status", status);
//        return mav;
//    }

    @POST
    @Path("/us")
    @Consumes (value = {MediaType.APPLICATION_JSON, })
    public Response contactUs(@Valid ContactUsForm form) {
//        if(error.hasErrors()) {
//            LOGGER.debug("Couldn't contact Hogar");
//            //return contactPage(form, "error");
//        }
        contactService.contactUS(form.getContent(), form.getMail(), form.getName());
        return Response.ok().build();
    }

    @POST
    @Path("/{id}")
    @Consumes (value = {MediaType.APPLICATION_JSON, })
    public Response contactEmployee(@Valid ContactForm form,  @PathParam("id") long id) throws UserNotFoundException, AlreadyExistsException {
//        if(error.hasErrors()) {
//            LOGGER.debug("Couldn't contact Hogar");
//            //return contactPage(form, "error");
//        }
        System.out.println("AaAAAAAAAAAAAAAAAA");
        try {
            userService.getUserById(id);
            System.out.println("BBBBBBBBBBBBBBB");
        } catch (ar.edu.itba.paw.model.exception.UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        employeeService.isEmployee(id);
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if(employee.isPresent()){
            employee.get().firstWordsToUpper();
            contactService.contact(employee.get().getId(), form.getContent(), "current user", form.getPhone());
        }
        return Response.ok().build();
    }
}
