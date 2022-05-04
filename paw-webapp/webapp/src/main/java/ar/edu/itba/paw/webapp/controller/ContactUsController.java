package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.ContactExistsException;
import ar.edu.itba.paw.service.*;
import ar.edu.itba.paw.webapp.form.ContactForm;
import ar.edu.itba.paw.webapp.form.ContactUsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ContactUsController {
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

    @RequestMapping("/contactUs")
    public ModelAndView contactPage(@ModelAttribute("contactUsForm") final ContactUsForm form) {
        final ModelAndView mav = new ModelAndView("contactUs");
        return mav;
    }
}
